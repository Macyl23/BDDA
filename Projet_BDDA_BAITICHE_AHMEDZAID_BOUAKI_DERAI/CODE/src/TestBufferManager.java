import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class TestBufferManager {
	public static void Main(String[]args) {
		// cette page sert de reference
		// a determiner demain 
		 PageId p = new PageId(0,0);
		 // je cree mon objet bufferManager via la methode car mon constructeur est priver 
		BufferManager buff = BufferManager.getLeBufferManager();
		// apelle de la methode getPage()
		try {
			buff.getPage(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buff.freePage(p, false);
		try {
			buff.flushhAll();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

