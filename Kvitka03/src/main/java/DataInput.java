import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class DataInput {

	private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	private static void writeText(String wr){
		if (wr == null)
			System.out.print("Введіть дані: ");
		else
			System.out.print(wr);
	}

	public static Long getLong() throws IOException{
		String s = getString();
		Long value = Long.valueOf(s);
		return value;
	}

	public static char getChar() throws IOException{
		String s = getString();
		return s.charAt(0);
	}

	public static char capitalizeUkrainianLetter(String wr) throws IOException {
		writeText(wr);
		String s = getString();

		if (s.isEmpty()) {
			System.out.println("You did not enter any character. Try again.");
			return capitalizeUkrainianLetter(wr); //
		}
		char ch = s.charAt(0);
		//
		if (ch >= 'а' && ch <= 'я' || ch == 'ґ') {
			return Character.toUpperCase(ch);
		}
		//
		return ch;
	}


	public static Integer getInt(String wr){
		writeText(wr);
		String s = "";
		try {
			s = getString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer value = Integer.valueOf(s);
		return value;

	}

	public static String getString() throws IOException {
		return br.readLine();
	}
}
