import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DiskManager {

	public PageId allocPage() throws IOException {
		PageId pid = new PageId();
		int i = 0;
		int page = 0;
		File f = new File(DBParams.DBPath + "/f" + i + ".bdda");
		boolean pageAvailaible = false;

		while (f.exists() && !pageAvailaible) {
			for (int j = 0; j < DBParams.maxPagesPerFile * 4; page++, j += 4) {
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

}
