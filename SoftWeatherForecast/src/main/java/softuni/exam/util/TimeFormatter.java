package softuni.exam.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalTime;

public class TimeFormatter extends XmlAdapter<String, LocalTime> {
    @Override
    public LocalTime unmarshal(String v) throws Exception {
        return new LocalTime(v);
    }

    @Override
    public String marshal(LocalTime v) throws Exception {
        return v.toString();
    }
}
