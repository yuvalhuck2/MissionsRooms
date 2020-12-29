package missions.room.Controllers;

public class UploadFileResponse {

    private String fileType;
    private long size;

    public UploadFileResponse(String contentType, long size) {
        this.fileType = contentType;
        this.size = size;
    }
}
