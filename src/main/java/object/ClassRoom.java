package object;

/*
 * 	教室信息
 */
public class ClassRoom {
	String roomName;          //教室名字   10教201
	String roomType;          //教室类型    多媒体教室
	int seatNum;               //座位数
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public int getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}
	
	
}
