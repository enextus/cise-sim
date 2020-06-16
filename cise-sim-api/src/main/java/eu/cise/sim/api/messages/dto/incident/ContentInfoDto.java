package eu.cise.sim.api.messages.dto.incident;

import java.io.Serializable;

public class ContentInfoDto  implements Serializable {

    private static final long serialVersionUID = 42L;

    private String content;
    private String mediaType;
    private String name;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
