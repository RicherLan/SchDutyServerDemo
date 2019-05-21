package netty.protocol.response.edu;

import netty.protocol.Packet;
import object.ClassRoom;

import static netty.protocol.command.Command.SearchEmptyClassroom_RESPONSE;

import java.util.Vector;

/*
 * 	查询空教室
 */
public class SearchEmptyClassroomResponsePacket extends Packet{

	String rString;
	Vector<ClassRoom> classRoom;
	@Override
	public int getCommand() {
		// TODO Auto-generated method stub
		return SearchEmptyClassroom_RESPONSE;
	}
	public Vector<ClassRoom> getClassRoom() {
		return classRoom;
	}
	public void setClassRoom(Vector<ClassRoom> classRoom) {
		this.classRoom = classRoom;
	}
	public String getrString() {
		return rString;
	}
	public void setrString(String rString) {
		this.rString = rString;
	}

}
