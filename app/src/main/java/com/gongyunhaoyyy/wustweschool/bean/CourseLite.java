package com.gongyunhaoyyy.wustweschool.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * @author: Gong Yunhao
 * @version: V1.0
 * @date: 2018/7/19
 * @github https://github.com/GongYunHaoyyy
 * @blog https://www.jianshu.com/u/52a8fa1f29fb
 */
public class CourseLite extends DataSupport implements Parcelable{
    private Course course;
    private long course_id;
    private String name;
    @Column(defaultValue = "unknown")
    private String room;
    private String time;
    private int startWeek;
    private int endWeek;
    @Column(defaultValue = "unknown")
    private String teacher;

    public CourseLite() {
    }

    public CourseLite(Course course) {
        this.course = course;
    }

    public CourseLite(String name, String room, String time, int startWeek, int endWeek, String teacher) {
        this.course = course;
        this.name = name;
        this.room = room;
        this.time = time;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.teacher = teacher;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
    }

    @Override
    public String toString() {
        return "CourseLite{" +
                "course=" + course +
                ", course_id=" + course_id +
                ", name='" + name + '\'' +
                ", room='" + room + '\'' +
                ", time='" + time + '\'' +
                ", startWeek=" + startWeek +
                ", endWeek=" + endWeek +
                ", teacher='" + teacher + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.course, flags);
        dest.writeLong(this.course_id);
        dest.writeString(this.name);
        dest.writeString(this.room);
        dest.writeString(this.time);
        dest.writeInt(this.startWeek);
        dest.writeInt(this.endWeek);
        dest.writeString(this.teacher);
    }

    protected CourseLite(Parcel in) {
        this.course = in.readParcelable(Course.class.getClassLoader());
        this.course_id = in.readLong();
        this.name = in.readString();
        this.room = in.readString();
        this.time = in.readString();
        this.startWeek = in.readInt();
        this.endWeek = in.readInt();
        this.teacher = in.readString();
    }

    public static final Parcelable.Creator<CourseLite> CREATOR = new Parcelable.Creator<CourseLite>() {
        @Override
        public CourseLite createFromParcel(Parcel source) {
            return new CourseLite(source);
        }

        @Override
        public CourseLite[] newArray(int size) {
            return new CourseLite[size];
        }
    };
}
