import java.util.ArrayList;
import java.util.List;

public class SnapshotArray extends ArrayList {

       SnapshotArray snapshotArray = null;
       List<SnapshotArray> snaps = new ArrayList<>();
        public SnapshotArray(int length) {
            SnapshotArray snapshotArr = new SnapshotArray(length);
        }

    public void set(int index, int val) {
            snapshotArray.set(index,val);
        }
        public int snap() {
            snaps.add(snapshotArray);
            return snaps.size()-1;
        }

        public int get(int index, int snap_id) {
            var snap =snaps.get(snap_id);
            return (int) snaps.get(index).get(index);
        }
    }

/**
 * Your SnapshotArray object will be instantiated and called as such:
 * SnapshotArray obj = new SnapshotArray(length);
 * obj.set(index,val);
 * int param_2 = obj.snap();
 * int param_3 = obj.get(index,snap_id);
 */

