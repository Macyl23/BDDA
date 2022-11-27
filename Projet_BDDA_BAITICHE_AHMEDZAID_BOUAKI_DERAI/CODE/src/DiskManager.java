import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class DiskManager {

	public ArrayList<PageId> pageDisponible = new ArrayList<PageId>();
	public static DiskManager leDiskManager = new DiskManager();
	public int count;
	public ArrayList<PageId> pageAlloues = new ArrayList<PageId>();

	/* Allocation d'une page
	 * @return L'index du fichier et la page
	 */
	private DiskManager(){};
	/**
	 * Méthode qui permet d'allouer une page 
	 * Nous recherchons une page libre
	 * si on trouve une page libre on return le pageId
	 * sinon on passe au fichier suivant
	 * si aucune page n'est libre on crée un fichier
	 * @return PageId
	 * @throws IOException
	 */
	public PageId allocPage() throws IOException {
		PageId pidTemp = new PageId();
		count++;
		int i = 0;
		int page = 0;
		File f = new File(DBParams.DBPath + ""+File.separator+"f" +i + ".bdda");
		boolean pageAvailaible = false;

		if(!pageDisponible.isEmpty()){
			System.out.println("J'ai des pages vides");
			PageId pid = pageDisponible.get(0);
			pageDisponible.remove(0);
			return pid;
		}

		if(!pageAlloues.isEmpty()){
			if(pageAlloues.get(pageAlloues.size()-1).pageIdx < DBParams.maxPagesPerFile){
				int p = pageAlloues.get(pageAlloues.size()-1).pageIdx+1;
				pageAlloues.add(new PageId(pageAlloues.get(pageAlloues.size()-1).fileIdx,p));
				return new PageId(pageAlloues.get(pageAlloues.size()-1).fileIdx,p);
			}else{
				int fPageId = pageAlloues.get(pageAlloues.size()-1).fileIdx+1;
				pageAlloues.add(new PageId(fPageId,0));
				return new PageId(fPageId,0);
			}
		}
		
		while (f.exists() && !pageAvailaible) {
			for (int j = 0; j < DBParams.pageSize * DBParams.maxPagesPerFile; page++, j += DBParams.pageSize) {
				
				if (f.length() == j) {
					pageAvailaible = true;
					pageAlloues.add(new PageId(i,page));
					return new PageId(i,page);

				}
			}

			if (!pageAvailaible) {
				i++;
				f = new File(DBParams.DBPath +""+ File.separator+"f" + i + ".bdda");
			}
		}
		if (!f.exists()) {
			RandomAccessFile file = new RandomAccessFile(DBParams.DBPath + ""+File.separator+"f" + i + ".bdda", "rw");
			pidTemp.fileIdx = i;
			pidTemp.pageIdx = 0;
			file.close();
		}
		pageAlloues.add(pidTemp);
		return pidTemp;
	}

	
	/**
	 * @param pid
	 * @param buff
	 * @throws IOException
	 * On accéde a la page dans le fichier qui est passé en params
	 * on vérifie si il y'a assez de place dans la buffer pour stocker de l'information
	 * si oui on lit ce qui a dans le fichier on le stocke dans le buffer
	 * sinon on lit octet par octet jusqu'a remplir le buffer
	 */
	public void readPage(PageId pid, ByteBuffer buff) throws IOException{
		RandomAccessFile f = new RandomAccessFile(DBParams.DBPath+""+File.separator+"f"+pid.fileIdx+".bdda", "r");
		int start = pid.pageIdx*DBParams.pageSize;
		f.seek(start);
		if(buff.array().length == DBParams.pageSize){
			f.read(buff.array());
		}else{
			for(int i=0; i< DBParams.pageSize; i++){
				buff.put(i,f.readByte());
			}

		}
		f.close(); 
	}

	
	/**
	 * @param pid
	 * @param buff
	 * @throws IOException
	 * Fonction qui permet d'écrire dans un fichier
	 * on ouvre le fichier et on place le curseur a la page précise
	 * on lit le contenu du buffer et on le stocke dans le fichier
	 */
	public void writePage(PageId pid, ByteBuffer buff) throws IOException{
		RandomAccessFile f = new RandomAccessFile(DBParams.DBPath+""+File.separator+"/f"+pid.fileIdx+".bdda", "rw");
		int start = pid.pageIdx*DBParams.pageSize;
		f.seek(start);
		f.write(buff.array());
		f.close();
	}

	public void deAllocPage(PageId pid){
		count--;
		pageDisponible.add(pid);
		System.out.println(pageDisponible.get(0).fileIdx +" "+ pageDisponible.get(0).pageIdx);
	}

	public int getCurrentCountAllocPages(){
		return count;
	}

	/**
	 * a verifier!!!
	 */
	public void menageDiscManager(){
		pageDisponible.clear();
		count=0;
	}
	
	
}
