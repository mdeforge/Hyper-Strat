import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 *     __  __                         _____ __             __
 *    / / / /_  ______  ___  _____   / ___// /__________ _/ /_
 *   / /_/ / / / / __ \/ _ \/ ___/   \__ \/ __/ ___/ __ `/ __/
 *  / __  / /_/ / /_/ /  __/ /      ___/ / /_/ /  / /_/ / /_
 * /_/ /_/\__, / .___/\___/_/      /____/\__/_/   \__,_/\__/
 *       /____/_/
 *
 * "How do you eat an elephant? One bite at a time!"
 */
public class hyperstrat {

    /**
     * @param args the command line arguments
     */

public static class gv {

    public static int ally_cnt;
    public static int tribe_cnt;
    public static int village_cnt;

    public static String[][] ally_text;
    public static String[][] village_text;
    public static String[][] tribe_text;
    
    public static int ally_fields;
    public static int tribe_fields;
    public static int village_fields;

    public static int r_ally;
    public static int r_tribe;
    public static int r_village;

    public static String search_string;

    public static int tmp_cnt;
}

    public static void main(String[] args) {

        //Download Required Data - Remmed out for time's sake.
        //download("http://en26.tribalwars.net/map/village.txt", "village.txt");
        //download("http://en26.tribalwars.net/map/tribe.txt", "tribe.txt");
        //download("http://en26.tribalwars.net/map/ally.txt", "ally.txt");

        //Set number of fields equal to data fields in file
        gv.ally_fields = 8;
        gv.tribe_fields = 6;
        gv.village_fields = 7;

        //Get the number of lines in each text document
        gv.ally_cnt = line_counter("ally.txt");
        gv.tribe_cnt = line_counter("tribe.txt");
        gv.village_cnt = line_counter("village.txt");
        
        //Make Corresponding Arrays
        gv.ally_text = new String[gv.ally_cnt][gv.ally_fields];
        gv.tribe_text = new String[gv.tribe_cnt][gv.tribe_fields];
        gv.village_text = new String[gv.village_cnt][gv.village_fields];

        ReadInFile("ally.txt", gv.ally_text, gv.ally_fields);
        ReadInFile("tribe.txt", gv.tribe_text, gv.tribe_fields);
        ReadInFile("village.txt", gv.village_text, gv.village_fields);

        //USER INPUT
        System.out.print("Enter Tribe Name (ex. Hegemony): ");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        gv.search_string = null;

        try {
            gv.search_string = br.readLine();
        } catch (IOException ioe) {
            System.out.println("There was a problem with your input");
            System.exit(1);
        }

        SortData(gv.ally_text, gv.tribe_text, gv.village_text);
}
    public static void SortData(String ally_text[][], String tribe_text[][], String village_text[][]) {
        for (gv.r_ally = 0; gv.r_ally < gv.ally_cnt-1; gv.r_ally++)
        {
            if (ally_text[gv.r_ally][1].equals(gv.search_string))
            {
                for (gv.r_tribe = 0; gv.r_tribe < gv.tribe_cnt-1; gv.r_tribe++)   
                {
                    if (tribe_text[gv.r_tribe][2].equals(ally_text[gv.r_ally][0]))
                    {
                        for (gv.r_village = 0; gv.r_village < gv.village_cnt-1; gv.r_village++)
                        { 
                            if (village_text[gv.r_village][4].equals(tribe_text[gv.r_tribe][0]))
                            {
                                System.out.println(/*"Tribe ID: " + ally_text[gv.r_ally][0] + */"(Tribe Member: [" + tribe_text[gv.r_tribe][1] + "]  (Village: [" + village_text[gv.r_village][1] + "]  (x|y: [" + "(" + village_text[gv.r_village][2] + "|" + village_text[gv.r_village][3] + ")])");
                            }
                        }
                    }
                }
            }
        }
    }

    public static int line_counter(String filename) {
        try {
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            gv.tmp_cnt = 0;

                while ((strLine = br.readLine()) != null)   {
                    gv.tmp_cnt++;
                }

           in.close();
           }catch (Exception e){//Catch exception if any
           System.err.println("Error line c: " + e.getMessage());
           }

        return gv.tmp_cnt;
    }

    public static void ReadInFile(String filename, String temp_array[][], int arrayRecords) {
        try {
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int temp_count = 0;
            int ii = 0;

                while ((strLine = br.readLine()) != null)   {
                    temp_count++;
                    String temp[] = strLine.split(",");
                    
                    for (int r = 0; r < arrayRecords; r++) {
                        temp_array[ii][r] = temp[r];
                    }
                    ii++;
            }
           in.close();
            }catch (Exception e){ //Catch exception if any
                System.err.println("Error in read f: " + e.getMessage());
            }
    }

	public static void download(String address, String localFileName) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream  in = null;

		try {
			URL url = new URL(address);
			out = new BufferedOutputStream(
				new FileOutputStream(localFileName));
			conn = url.openConnection();
			in = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int numRead;
			long numWritten = 0;
			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
				numWritten += numRead;
			}
			System.out.println(localFileName + "\t" + numWritten);
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ioe) {
			}
		}
	}
	public static void download(String address) {
		int lastSlashIndex = address.lastIndexOf('/');
		if (lastSlashIndex >= 0 &&
		    lastSlashIndex < address.length() - 1) {
			download(address, address.substring(lastSlashIndex + 1));
		} else {
			System.err.println("Could not figure out local file name for " +
				address);
		}
	}
}


