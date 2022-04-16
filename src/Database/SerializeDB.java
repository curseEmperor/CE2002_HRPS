package Database;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import java.util.ArrayList;

public class SerializeDB {

	public static List loadData(String filename) {
		List pDetails = new ArrayList<>();
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(filename);
			ois = new ObjectInputStream(fis);

			int noOfRecords = ois.readInt();
			System.out.println(noOfRecords + " Entries Loaded");

			for (int i = 0; i < noOfRecords; i++) {
				pDetails.add(ois.readObject());
			}
			ois.close();

		} catch (IOException ex) {
			// ex.printStackTrace();
			System.out.println("Serializable file not found.");
			System.out.println("Proceeding with new Database.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return pDetails;
	}

	public static void storeData(String filename, List list) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);

			out.writeInt(list.size());
			for (Object entities : list) {
				out.writeObject(entities);
			}

			System.out.println("--Entries Saved--\n");
			// out.writeObject(list);
			out.close();
			// System.out.println("Object Persisted");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
