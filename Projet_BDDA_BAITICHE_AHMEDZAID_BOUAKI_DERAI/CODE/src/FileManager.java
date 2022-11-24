import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManager {

  // CONSTRUCTEUR FileManager PRIVATE
  private FileManager() {
  }

  // instance de ma classe
  public static FileManager leFileManager = new FileManager();

  // • L’allocation d’une nouvelle page via AllocPage du DiskManager.
  // C’est le PageId rendu par AllocPage qui devra aussi être rendu par
  // createNewHeaderPage
  public PageId createNewHeaderPage() throws IOException {
    PageId pageIdFile = DiskManager.leDiskManager.allocPage();
    BufferManager.leBufferManager.initBuffPool();
    ByteBuffer bufferHeaderPage = BufferManager.leBufferManager.getPage(pageIdFile);
    bufferHeaderPage.putInt(0, 0);
    
    DiskManager.leDiskManager.writePage(pageIdFile, bufferHeaderPage);
    BufferManager.leBufferManager.freePage(pageIdFile, true);
    return pageIdFile;
  }

  public PageId addDataPage(RelationInfo r) throws IOException {
    PageId pid = DiskManager.leDiskManager.allocPage();
    BufferManager.leBufferManager.buffPoolContenu();
    ByteBuffer bufferDataPage = BufferManager.leBufferManager.getPage(pid); // Data Page

    /* DATA PAGE */
    // Ecrire 8 octets de 0
    bufferDataPage.putInt((DBParams.pageSize) - 8, 0);
    bufferDataPage.putInt((DBParams.pageSize) - 4, 0);

    BufferManager.leBufferManager.freePage(pid, true);

    /* HEADER PAGE */

    // Rechercher l'espace disponbile
    ByteBuffer bufferHeaderPage = BufferManager.leBufferManager.getPage(r.getHeaderPageId());
    int dataPage = bufferHeaderPage.getInt(0);
    int espaceDispo = (dataPage * 12 + 4);

    // Ajouter l'id data page et l'espace disponible
    bufferHeaderPage.putInt(espaceDispo, pid.fileIdx);
    bufferHeaderPage.putInt(espaceDispo + 4, pid.pageIdx);
    bufferHeaderPage.putInt(DBParams.pageSize - 8);

    // Incrémenter data page
    dataPage++;
    bufferHeaderPage.putInt(0, dataPage);



    BufferManager.leBufferManager.freePage(pid, true);

    return pid;
  }

  /*
   Cette méthode doit retourner, pour la relation désignée par relInfo, le
   PageId d’une page de données
   sur laquelle il reste assez de place pour insérer le record ; si une telle
   page n’existe pas, la méthode
   retournera null.
   */
  public PageId getFreeDataPageId(RelationInfo r, int sizeRecord) throws IOException {
    // recupere byteBuffer de la relation
    PageId pageId = new PageId();
    int j = 0;
    ByteBuffer bufferHeaderPage = BufferManager.leBufferManager.getPage(r.getHeaderPageId());
    // verifier que le ByteBuffer contient page tel que sizeRecord correspondant à
    // la taille du record à insérer de la page
   
      for (int i = 12; i < bufferHeaderPage.capacity(); i += 12) {
        if (bufferHeaderPage.getInt(i) >= sizeRecord) {
          j = i - 8;
          pageId.pageIdx = bufferHeaderPage.getInt(j);
          j = i + 4;
          pageId.fileIdx = bufferHeaderPage.getInt(j);
          BufferManager.leBufferManager.freePage(r.getHeaderPageId(), false);
          return pageId;
        }
      }
      BufferManager.leBufferManager.freePage(r.getHeaderPageId(), false);
      return FileManager.leFileManager.addDataPage(r);
    
   
  }

  public RecordId writeRecordToDataPage(Record r, PageId pid)
    throws IOException {
    ByteBuffer bufferDataPage = BufferManager.leBufferManager.getPage(pid);
    
    /*
     * Recherche de la position de l'espace dispo et ecriture du record
     */
    int positionDispo = bufferDataPage.getInt(DBParams.pageSize - 4);
    System.out.println(positionDispo);
    r.writeToBuffer(bufferDataPage, positionDispo);

    /*Recherche du la position ou les slots commencent
     * Ecriture de la position du debut du record
     * Ecriture de la taille du record
     */
    int nbSlot = bufferDataPage.getInt(DBParams.pageSize - 8);
    int positionInsertionSlot = (DBParams.pageSize - 8) - ((nbSlot+1) * 8);
    bufferDataPage.putInt(positionInsertionSlot, positionDispo);
    bufferDataPage.putInt(positionInsertionSlot + 4, r.getWrittenSize());

    /*
     * Recuperer la valeur du nombre du slot
     * Incrementer le nombre de slot
     */
    bufferDataPage.putInt(DBParams.pageSize - 8, nbSlot + 1);

    /*
     * Mise a jour de la position de l'espace disponible
     */
    positionDispo = positionDispo + r.getWrittenSize();
    bufferDataPage.putInt(DBParams.pageSize - 4, positionDispo);

    BufferManager.leBufferManager.freePage(pid, true);

    /*
     * Mise a jour de la header Page
     * Incrementer le nombre de data page
     */
    ByteBuffer bufferHeaderPage = BufferManager.leBufferManager.getPage(r.getRelInfo().getHeaderPageId());
    int espaceLibre = bufferHeaderPage.getInt(0) * 12;
    bufferHeaderPage.putInt(espaceLibre, pid.pageIdx);
    bufferHeaderPage.putInt(espaceLibre + 4, pid.fileIdx);

    /*
     * Recherche espace dispo pour ajouter
     * ID Data page et nb octets libre
     * Ajout du ID DATAPAGE
     * Ajout des octets libre
     */
    int espaceDispo = bufferHeaderPage.getInt(0) * 12 + 4;
    bufferHeaderPage.putInt(espaceDispo, pid.pageIdx);
    bufferHeaderPage.putInt(espaceDispo + 4, pid.fileIdx);

    int nbOctetsLibre = DBParams.pageSize - 8 - nbSlot - positionDispo;
    bufferHeaderPage.putInt(espaceDispo + 8, nbOctetsLibre);

    
    BufferManager.leBufferManager.freePage(
      r.getRelInfo().getHeaderPageId(),
      true
    );

    return new RecordId(pid, nbSlot);
  }

  public ArrayList<Record> getRecordsInDataPage(RelationInfo r, PageId pid)
    throws IOException {
    ByteBuffer bufferDataPage = BufferManager.leBufferManager.getPage(pid);
    int posDebutRecord = 0;

    /* La liste qu'on devra renvoyer avec un record temporaire pour lire dans le buffer */
    ArrayList <Record> listeRecords = new ArrayList<>();
    Record recordTemp = new Record(r);

    /* On doit recuperer le nombre de records qui est nbSlot
     * Trouver la position ou le premier record commence
     */
    int nbSlot = bufferDataPage.getInt(DBParams.pageSize - 8);
    int posDebutSlot = DBParams.pageSize - 8 - nbSlot * 8;

    /*On boucle jusqu'a ce qu'on ait lu tous les slots
     * On lit le contenu du buffer pour trouver la position de debut du record
     * On stocke le record lu du buffer dans un record temporaire
     * On ajoute le record dans la liste
     * Et on passe a la posiiton de debut du prochain record
     */
    posDebutRecord = bufferDataPage.getInt(posDebutSlot);
    for (int i =0; i < nbSlot; i++) {
      recordTemp.readFromBuffer(bufferDataPage, posDebutRecord);
      System.out.println("recordTemp: "+recordTemp);
      listeRecords.add(recordTemp);
      posDebutRecord = bufferDataPage.getInt(posDebutSlot+8);
    }
    for( int i=0 ; i<nbSlot ; i++){
      System.out.println("contenu liste record: "+listeRecords.get(i));
    }
    
    BufferManager.leBufferManager.freePage(pid, true);
    return listeRecords;
  }

  public ArrayList<PageId> getAllDataPage(RelationInfo r) throws IOException {
    /*HEADER PAGE */
    ByteBuffer bufferHeaderPage = BufferManager.leBufferManager.getPage(
      r.getHeaderPageId()
    );
    System.out.println("buffer header page"+Arrays.toString(bufferHeaderPage.array())); 
    BufferManager.leBufferManager.buffPoolContenu();
    /*
     * On recupere le nombre de data page en lisant les 4 premiers octets du header page
     * On initialiste la position de debut du pageId de la data page une qui s'agit de 4
     * La liste qu'on devra retourner
     */
    int nbDataPage = bufferHeaderPage.getInt(0), positionDataPage = 4;
    ArrayList<PageId> listeDataPage = new ArrayList<>();
    PageId pidTemp = new PageId();

    /*Tant que on a pas lu le nombre de data Page
     * On recupere la valeur du PageId de la data page i
     * On l'ajoute dans l'ArrayList
     * on déplace la position de 12 car entre le pageId d'une dataPage et le suivant
     * Il y'a 12 octets occupés
     */
    for (int i = 0; i < nbDataPage; i++) {
      
      pidTemp = new PageId(bufferHeaderPage.getInt(positionDataPage),bufferHeaderPage.getInt(positionDataPage+4));
      listeDataPage.add(pidTemp);
      positionDataPage += 12;
    }
    BufferManager.leBufferManager.freePage(r.getHeaderPageId(), true);
    return listeDataPage;
  }

  /*Inserer un record dans une relation
   * On renvoie le rid
   * Du record qu'on veut ecrire dans la premiere dataPage qui est libre
   * On recupere la dataPage libre en lancant la fonction getFreeDataPageId
   * Acec en parametres la relation info et la taille du record
   */
  public RecordId insertRecordIntoRelation(Record r) throws IOException {
    return writeRecordToDataPage(r,getFreeDataPageId(r.getRelInfo(), r.recordSizeFromValues()));
  }

  /*Fonction qui permet de recuperer tous les records d'une relation */
  public ArrayList<Record> getAllRecords(RelationInfo r) throws IOException {
    /* La liste qu'on devra retourner
     * On stocke toutes les data page dans une arraylist
     * qu'on recupere depuis la fonction getAllDataPage
     */
    ArrayList<Record> listeRecords = new ArrayList<>();
    ArrayList<PageId> dataPage = getAllDataPage(r);
    ArrayList<Record> listeTemp = new ArrayList<>();

    /*Boucle imbriquée
     * La premiere qui permet de parcourir la liste de dataPage
     * Et stocker dans listeTemp tous les records de cette dataPage
     * La deuxieme pour stocker tous les records dans la liste qu'on voudra return
     */
    
    for (int i = 0; i < dataPage.size(); i++) {
      listeTemp = getRecordsInDataPage(r, dataPage.get(i));
      for (int j = 0; j < listeTemp.size(); j++) {
        listeRecords.add(listeTemp.get(j));
      }
    }
    return listeRecords;
  }
}
