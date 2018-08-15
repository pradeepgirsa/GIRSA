//package za.co.global.domain.gui;
//
//import org.hibernate.annotations.ColumnDefault;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Objects;
//
//@Entity(name = "gui_menu")
//public class Menu implements Serializable{
//
//    private static final long serialVersionUID = 5500456340911177632L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "name", nullable = false)
//    private String name;
//
//    @Column(name = "href")
//    private String href;
//
//    @Column(name = "icon")
//    private String icon;
//
//    @Column(name = "class_type")
//    private String classType;
//
//    @Column(name = "javascript_function", length = 45)
//    private String javascriptFunction;
//
//    @ColumnDefault("true")
//    @Column(name = "active")
//    private Boolean active;
//
//    @Column(name = "notification_count")
//    private Integer notificationCount;
//
//    @Column(name = "has_role", length = 255)
//    private String has_role;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="parent_id")
//    private Menu parent;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getHref() {
//        return href;
//    }
//
//    public void setHref(String href) {
//        this.href = href;
//    }
//
//    public String getIcon() {
//        return icon;
//    }
//
//    public void setIcon(String icon) {
//        this.icon = icon;
//    }
//
//    public String getClassType() {
//        return classType;
//    }
//
//    public void setClassType(String classType) {
//        this.classType = classType;
//    }
//
//    public String getJavascriptFunction() {
//        return javascriptFunction;
//    }
//
//    public void setJavascriptFunction(String javascriptFunction) {
//        this.javascriptFunction = javascriptFunction;
//    }
//
//    public Boolean getActive() {
//        return active;
//    }
//
//    public void setActive(Boolean active) {
//        this.active = active;
//    }
//
//    public Integer getNotificationCount() {
//        return notificationCount;
//    }
//
//    public void setNotificationCount(Integer notificationCount) {
//        this.notificationCount = notificationCount;
//    }
//
//    public String getHas_role() {
//        return has_role;
//    }
//
//    public void setHas_role(String has_role) {
//        this.has_role = has_role;
//    }
//
//    public Menu getParent() {
//        return parent;
//    }
//
//    public void setParent(Menu parent) {
//        this.parent = parent;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Menu menu = (Menu) o;
//        return Objects.equals(id, menu.id) &&
//                Objects.equals(name, menu.name) &&
//                Objects.equals(href, menu.href) &&
//                Objects.equals(icon, menu.icon) &&
//                Objects.equals(classType, menu.classType) &&
//                Objects.equals(javascriptFunction, menu.javascriptFunction) &&
//                Objects.equals(active, menu.active) &&
//                Objects.equals(notificationCount, menu.notificationCount) &&
//                Objects.equals(has_role, menu.has_role) &&
//                Objects.equals(parent, menu.parent);
//    }
//
//    @Override
//    public int hashCode() {
//
//        return Objects.hash(id, name, href, icon, classType, javascriptFunction, active, notificationCount, has_role, parent);
//    }
//
//    @Override
//    public String toString() {
//        return "Menu{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", href='" + href + '\'' +
//                ", icon='" + icon + '\'' +
//                ", classType='" + classType + '\'' +
//                ", javascriptFunction='" + javascriptFunction + '\'' +
//                ", active=" + active +
//                ", notificationCount=" + notificationCount +
//                ", has_role='" + has_role + '\'' +
//                ", parent=" + parent +
//                '}';
//    }
//}
