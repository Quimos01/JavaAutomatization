package org.example.kvitka01;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

@WebServlet(name="systemInfo", value="/system-info")
public class SystemInfo extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        Runtime runtime = Runtime.getRuntime();

        int mb = 1024 * 1024;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<h1 style=\"color: red\">" + "Деталі комп'ютера:" + "</h1>");

        out.println("<h3>Процесор та ОС:</h3>");
        out.println("<p>Операційна система: " + os.getName() + "</p>");
        out.println("<p>Процесор (кількість ядер): " + os.getAvailableProcessors() + "</p>");

        out.println("<h3>Пам'ять:</h3>");
        out.println("<p>Вся RAM в ПК):</b> " + (os.getTotalMemorySize() / mb) + " MB</p>");
        out.println("<p>Вільна RAM:</b> " + (os.getFreeMemorySize() / mb) + " MB</p>");

        out.println("<h3>Пам'ять для Java:</h3>");
        out.println("<p>Виділена пам'ять: " + (runtime.maxMemory() / mb) + "MB</p>");
        out.println("<p>Використана пам'ять: " + ((runtime.totalMemory() - runtime.freeMemory()) / mb) + " MB</p>");        out.println("</body>");

        out.println("<h3>Жорсткі диски:</h3>");
        File[] roots = File.listRoots();
        for (File root : roots) {
            out.println("<p>Диск " + root.getAbsolutePath() + "</p>");
            out.println("<p>Загальний об'єм: " + (root.getTotalSpace() / mb / 1024) + " GB</p>");
            out.println("<p>Вільне місце: " + (root.getFreeSpace() / mb / 1024) + " GB</p>");
        }
        out.println("</html>");
    }
}
