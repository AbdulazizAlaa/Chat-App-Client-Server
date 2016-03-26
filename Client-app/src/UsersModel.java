import java.util.ArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class UsersModel implements ListModel<String>{

	public ArrayList<String> users;
	
	public UsersModel() {
		// TODO Auto-generated constructor stub
		users = new ArrayList<String>();
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return users.size();
	}

	@Override
	public String getElementAt(int index) {
		// TODO Auto-generated method stub
		return users.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

}
