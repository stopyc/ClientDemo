package cn.qgstudio.po;

/**
 * @program: qg_mid_test
 * @description:
 * @author: stop.yc
 * @create: 2022-07-29 15:50
 **/
public class Network {
    private String name;
    private String displayName;

    @Override
    public String toString() {
        return "Network{" +
                "name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", mac='" + mac + '\'' +
                ", virtual=" + virtual +
                ", up=" + up +
                '}';
    }

    public String getName() {
        return name;
    }

    public Network() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public Network(String name, String displayName, String mac, boolean virtual, boolean up) {
        this.name = name;
        this.displayName = displayName;
        this.mac = mac;
        this.virtual = virtual;
        this.up = up;
    }

    private String mac;
    private boolean virtual;
    private boolean up;
}
