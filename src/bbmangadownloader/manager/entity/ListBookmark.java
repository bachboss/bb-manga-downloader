/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbmangadownloader.manager.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author Bach
 */
@XmlRootElement
@XmlSeeAlso({Bookmark.class})
public class ListBookmark extends ArrayList<Bookmark> {

    public ListBookmark() {
    }

    @XmlElement(name = "list")
    public List<Bookmark> getBookmarks() {
        return this;
    }
}
