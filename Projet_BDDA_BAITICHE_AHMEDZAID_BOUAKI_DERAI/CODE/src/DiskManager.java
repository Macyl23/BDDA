import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class DiskManager {

	public static DiskManager leDiskManager = new DiskManager();
	/* Allocation d'une page
	 * @return L'index du fichier et la page
	 */
	private DiskManager(){};
	public PageId allocPage() throws IOException {
		PageId pid = new PageId();
		int i = 0;
		int page = 0;
		File f = new File(DBParams.DBPath + "/f" + i + ".bdda");
		boolean pageAvailaible = false;

		while (f.exists() && !pageAvailaible) {
			for (int j = 0; j < DBParams.maxPagesPerFile * 4; page++, j += DBParams.pageSize) {
				if (f.length() == j) {
					pageAvailaible = true;
					return new PageId(i,page);
				}
			}
			if (!pageAvailaible) {
				i++;
				f = new File(DBParams.DBPath + "/f" + i + ".bdda");
			}
		}
		if (!f.exists()) {
			RandomAccessFile file = new RandomAccessFile(DBParams.DBPath + "/f" + i + ".bdaa", "rw");
			pid.fileIdx = i;
			pid.pageIdx = 0;
			file.close();
		}

		return pid;
	}

	/* Lecture d'une page
	 * @params: PageId, ByteBuffer
	 * On accéde a la page dans le fichier qui est passé en params
	 * on vérifie si il y'a assez de place dans la buffer pour stocker de l'information
	 * si ou on lit ce qui a dans le fichier on le stocke dans le buffer
	 * sinon on lit octet par octet jusqu'a remplier le buffer
	 */
	public void readPage(PageId pid, ByteBuffer buff) throws IOException{
		RandomAccessFile f = new RandomAccessFile(DBParams.DBPath+"/f"+pid.fileIdx+".bdda", "r");
		int start = pid.pageIdx;
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

	/* Ecriture dans un fichier 
	 * 
	 */
	public void writePage(PageId pid, ByteBuffer buff){

	}

	public void deAllocPage(PageId pid){

	}

	public int getCurrentCountAllocPages(){
		return 0;
	}
}
