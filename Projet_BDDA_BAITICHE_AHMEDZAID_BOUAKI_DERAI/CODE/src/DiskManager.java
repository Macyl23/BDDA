import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;


public class DiskManager {
	private PageId pageId;
	public DiskManager() {		
		//	 initialisation de l'instance
	}

	// METHODE 
	public PageId allocPage() throws IOException {
		// chercher uhn fichier dans notre db 
		RandomAccessFile file = new RandomAccessFile("rw","C:\\\\Users\\\\204BO\\\\OneDrive\\\\Bureau\\\\BDDA\\\\Projet_BDDA_BAITICHE_AHMEDZAID_BOUAKI_DERAI\\\\DB");
		PageId pageId = new PageId();
		// verifier que le fichier a des pages vides ou non complet
		// a revoir le bloc if
		if(file.length() < 4096*4) {
			for(int i = (int) (4096*4 - file.length()/4096); i <= 0 ; i--) {
			// allocation / reservation d'une nouvelle page
			byte pageSizeOctet = (byte) (4096*4 - file.length()/4096);
			ByteBuffer.allocate((int)pageSizeOctet);
			// initialisation de lap
			pageId.setPageIdx((int)pageSizeOctet) ;
			}
		}
		else {
			CreeFichiers nouveauxFichiers  ;
		}
		return pageId;

	}
	public void readPage(PageId Id,byte [] buff) {



	}
	public void writePage(PageId pageIdx, Byte [] buff) {


	}
	public void deallocPage(/*pageId*/) {

	}
	public int getCurrentCountAllocPage() {
		return 0;
	}


}