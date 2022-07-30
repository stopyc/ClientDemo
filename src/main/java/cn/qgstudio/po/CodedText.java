package cn.qgstudio.po;

import java.util.Date;
import java.util.List;

/**
 * @program: ClientDemo
 * @description:
 * @author: stop.yc
 * @create: 2022-07-29 16:06
 **/
public class CodedText {
    private int software_id;
    private int function_type;
    private int version_id;
    private Date now;
    private List<String> macs;
    private String cpu;
    private String hard;

    @Override
    public String toString() {
        return "CodedText{" +
                "software_id=" + software_id +
                ", function_type=" + function_type +
                ", version_id=" + version_id +
                ", now=" + now +
                ", macs=" + macs +
                ", cpu='" + cpu + '\'' +
                ", hard='" + hard + '\'' +
                '}';
    }

    public CodedText() {
    }

    public int getSoftware_id() {
        return software_id;
    }

    public void setSoftware_id(int software_id) {
        this.software_id = software_id;
    }

    public int getFunction_type() {
        return function_type;
    }

    public void setFunction_type(int function_type) {
        this.function_type = function_type;
    }

    public int getVersion_id() {
        return version_id;
    }

    public void setVersion_id(int version_id) {
        this.version_id = version_id;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public List<String> getMacs() {
        return macs;
    }

    public void setMacs(List<String> macs) {
        this.macs = macs;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getHard() {
        return hard;
    }

    public void setHard(String hard) {
        this.hard = hard;
    }

    public CodedText(int software_id, int function_type, int version_id, Date now, List<String> macs, String cpu, String hard) {
        this.software_id = software_id;
        this.function_type = function_type;
        this.version_id = version_id;
        this.now = now;
        this.macs = macs;
        this.cpu = cpu;
        this.hard = hard;
    }
}
