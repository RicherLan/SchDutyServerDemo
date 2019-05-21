package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import object.ListViewScore;

import static netty.protocol.command.Command.GetScoreOfUID_RESPONSE;

import java.util.Vector;

/*
    获取成绩
 */
public class GetScoreOfUIDResponsePacket extends Packet {
	
	String result;
	int grade;
	int xueqi;
	Vector<ListViewScore> scores;
    @Override
    public int getCommand() {

        return GetScoreOfUID_RESPONSE;
    }

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getXueqi() {
		return xueqi;
	}

	public void setXueqi(int xueqi) {
		this.xueqi = xueqi;
	}

	public Vector<ListViewScore> getScores() {
		return scores;
	}

	public void setScores(Vector<ListViewScore> scores) {
		this.scores = scores;
	}

}
