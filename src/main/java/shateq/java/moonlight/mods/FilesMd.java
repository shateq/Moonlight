package shateq.java.moonlight.mods;

import shateq.java.moonlight.MoonlightBot;
import shateq.java.moonlight.util.Module;

public class FilesMd extends Module {
    public FilesMd(Settings settings) {
        super(settings);
    }

    @Override
    public void start() {
        if (this.available()) {
            MoonlightBot.getJDA().getEventManager().register(this);
        }
    }

//        final Module files = launcher.getModule("files");
//        if(files != null && files.getStatus().equals(Status.ON)) {
//            if(!msg.getAttachments().isEmpty()) {
//                final Collection<Message.Attachment> attachments = msg.getAttachments();
//                for(Message.Attachment a : attachments) {
//                    if(a.getFileExtension() != null) {
//                        if (badExtension(a.getFileExtension())) {
//                            msg.delete().reason("Small Exception");
//                        }
//                    }
//                }
//            }
//        }

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
