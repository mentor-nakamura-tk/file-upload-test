import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * ファイルのアップロードテスト.
 *
 * <pre>
 * MultipartConfigのパラメータ
 *・location - アップロードファイルを一時的に保存するフォルダパス
 *・maxFileSize - アップロードファイルの最大サイズ(バイト)
 * <pre>
 *
 * @author takumi-nakamura
 * @see https://qiita.com/ohke/items/bec00a69d3f538aab06b
 *
 */
@WebServlet("/UploadServlet")
@MultipartConfig(location="/tmp", maxFileSize=1048576)
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart("file");
        String fineName = this.getFileName(part);
        part.write("C:/temp/" + fineName);
        response.sendRedirect("upload.jsp"); // アップロード画面に戻す.
    }

    private String getFileName(Part part) {
        String fineName = null;
        for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
            if (dispotion.trim().startsWith("filename")) {
            	fineName = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
            	fineName = fineName.substring(fineName.lastIndexOf("\\") + 1);
                break;
            }
        }
        return fineName;
    }
}