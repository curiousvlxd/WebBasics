package step.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.services.MimeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

@Singleton
public class ImageServlet extends HttpServlet {
    @Inject
    private MimeService mimeService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestedFile = req.getPathInfo();
        int dotPosition = requestedFile.lastIndexOf('.');
        if (dotPosition == -1) {
            resp.setStatus(400);
            resp.getWriter().print("Files without extension are not supported");
            return;
        }
        String extension = requestedFile.substring(dotPosition+1);
        if(!mimeService.isImage(extension)){
            resp.setStatus(400);
            resp.getWriter().print("File type is not supported " + extension);
            return;
        }
        String path = req.getServletContext().getRealPath("/");
        File file = new File(path + "/../Uploads" + requestedFile);
        if (file.isFile() && file.canRead()) {
            resp.setContentType(mimeService.getMimeType(extension));
            resp.setContentLengthLong(file.length());
            try(InputStream reader = Files.newInputStream(file.toPath())) {
               OutputStream writer = resp.getOutputStream();
                byte[] buffer = new byte[2048];
                int bytesRead;
                while ((bytesRead = reader.read(buffer)) > 0) {
                    writer.write(buffer, 0, bytesRead);
                }
            }
            catch(IOException e) {
                resp.setStatus(500);
                resp.getWriter().println("Server error:");
                System.out.println("ImagesServlet::doGet " + requestedFile + "\n" + e.getMessage());
            }
        }
        else {
            resp.setStatus(404);
            resp.getWriter().println("File not found " + requestedFile);
        }
    }
}
    

