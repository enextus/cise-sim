package eu.cise.emulator.app.core;

import java.util.Objects;

public class XmlFileReference {
    private String path;
    private String name;
    private Integer hash;

    public XmlFileReference() {
    }

    public XmlFileReference(String name, String path) {
        this.name = name;
        this.path = path;
        this.hash = new Integer(Objects.hash(path));
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String id) {
        this.path = id;
    }

    public int getHash() {
        if (this.hash==null) { this.hash = new Integer(Objects.hash(path)); }
        return this.hash;
    }

    public static int getCalculedHash(String path){
        return Objects.hash(path);
    }



    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if(null == o || o.getClass() != this.getClass()) return false;
        XmlFileReference other = (XmlFileReference) o;
        return Objects.equals(this.getHash(), other.getHash());
    }
}
