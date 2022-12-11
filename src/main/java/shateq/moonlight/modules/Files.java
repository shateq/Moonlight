package shateq.moonlight.modules;

import org.jetbrains.annotations.NotNull;
import shateq.moonlight.util.Identifier;

public class Files extends Module {
    public Files(@NotNull Identifier id, Status status) {
        super(id, status);
    }

    /*final Module files = launcher.getModule("files");
    if(files != null && files.status.equals(Status.ON)) {
        if(!msg.getAttachments().isEmpty()) {
            final Collection<Message.Attachment> attachments = msg.getAttachments();
            for(Message.Attachment a : attachments) {
                if(a.getFileExtension() != null) {
                    if (badExtension(a.getFileExtension())) {
                        msg.delete().reason("Small Exception");
                    }
                }
            }
        }
    }*/
    private enum Type {
        mov, bat, ahk, dll, exe, py, msi, mp4https;

        boolean badExtension(String e) {
            for (Type t : Type.values()) {
                if (t.name().equals(e)) {
                    return true;
                }
            }
            return false;
        }
    }
}
