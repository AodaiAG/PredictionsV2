package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import pSystem.engine.World;
import utils.ServletUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@WebServlet("/upload-file")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class XmlUploadServlet extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            // Get the uploaded XML file from the request
            InputStream fileContent = request.getPart("file1").getInputStream();

            // Create a DocumentBuilder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Parse the XML file into a Document
            Document doc = dBuilder.parse(new InputSource(fileContent));

            World engine=ServletUtils.getEngine(getServletContext()).ParseXmlAndLoadWorldFromDoc(doc);

            response.getWriter().write("XML uploaded and processed successfully!");
        } catch (Exception e)
        {
            response.getWriter().write(e.getMessage());
        }
    }
}