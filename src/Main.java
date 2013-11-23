import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.db.manager.Manager;
import org.db.manager.Query;
import org.db.manager.Result;

public class Main {

	public static void main(String[] args) throws IOException {

		call(args);
	}

	public static void call(String... args) throws IOException {
		if (args.length == 0) {
			System.err.println("Usage: java Main <sql query> [output file]");
		} else {
			String sql = args[0];
			OutputStream out = System.out;
			if (args.length > 1) {
				try {
					out = new FileOutputStream(args[1]);
				} catch (IOException ex) {
					System.err.println(ex.getMessage());
				}
			}
			executeSql(sql, out);
			out.close();
		}
	}

	private static void executeSql(String sql, OutputStream out) {
		try {
			Properties props = getPropsFromConfig();

			Manager.initJDBCDriver(props.getProperty("driver"),
					props.getProperty("url"), props.getProperty("username"),
					props.getProperty("password"));

			Query query = Manager.createQuery(sql);
			Result result = query.execute();
			result.print(out);
			query.close();

		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}

	private static Properties getPropsFromConfig()
			throws FileNotFoundException, IOException {
		String configFile = System.getProperty("user.home")
				+ System.getProperty("file.separator") + ".db-manager.conf";

		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(configFile);
		props.load(fis);
		fis.close();
		return props;
	}
}
