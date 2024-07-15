import java.util.ArrayList;
import java.util.List;
class Interval {
    int start;
    int end;

    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
class IntervalTreeNode {
    Interval interval;
    int maxEnd; // Maximum end value in the subtree rooted at this node
    IntervalTreeNode left;
    IntervalTreeNode right;

    public IntervalTreeNode(Interval interval) {
        this.interval = interval;
        this.maxEnd = interval.end;
        this.left = null;
        this.right = null;
    }
}

class IntervalTree {
    private IntervalTreeNode root;

    public IntervalTree() {
        this.root = null;
    }

    // Helper function to update maxEnd values in the tree
    private void updateMaxEnd(IntervalTreeNode node) {
        if (node != null) {
            int maxChildEnd = 0;
            if (node.left != null) {
                maxChildEnd = Math.max(maxChildEnd, node.left.maxEnd);
            }
            if (node.right != null) {
                maxChildEnd = Math.max(maxChildEnd, node.right.maxEnd);
            }
            node.maxEnd = Math.max(node.interval.end, maxChildEnd);
        }
    }

    // Insert an interval into the tree
    public void insertInterval(int start, int end) {
        Interval interval = new Interval(start, end);
        this.root = insertInterval(root, interval);
    }

    private IntervalTreeNode insertInterval(IntervalTreeNode node, Interval interval) {
        if (node == null) {
            return new IntervalTreeNode(interval);
        }

        if (interval.start < node.interval.start) {
            node.left = insertInterval(node.left, interval);
        } else {
            node.right = insertInterval(node.right, interval);
        }

        updateMaxEnd(node);
        return node;
    }

    // Delete an interval from the tree
    public void deleteInterval(int start, int end) {
        this.root = deleteInterval(root, new Interval(start, end));
    }

    private IntervalTreeNode deleteInterval(IntervalTreeNode node, Interval interval) {
        if (node == null) {
            return null;
        }

        if (interval.start < node.interval.start) {
            node.left = deleteInterval(node.left, interval);
        } else if (interval.start > node.interval.start) {
            node.right = deleteInterval(node.right, interval);
        } else { // Found the interval to delete
            if (interval.end != node.interval.end) {
                node.right = deleteInterval(node.right, interval);
            } else {
                if (node.left == null) {
                    return node.right;
                } else if (node.right == null) {
                    return node.left;
                }

                IntervalTreeNode minNode = findMin(node.right);
                node.interval = minNode.interval;
                node.right = deleteInterval(node.right, minNode.interval);
            }
        }

        updateMaxEnd(node);
        return node;
    }

    // Find the minimum node in a subtree
    private IntervalTreeNode findMin(IntervalTreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Find overlapping intervals with a given interval [start, end]
    public List<Interval> findOverlappingIntervals(int start, int end) {
        List<Interval> result = new ArrayList<>();
        findOverlappingIntervals(root, start, end, result);
        return result;
    }

    private void findOverlappingIntervals(IntervalTreeNode node, int start, int end, List<Interval> result) {
        if (node == null) {
            return;
        }

        // Check if node's interval overlaps with [start, end]
        if (node.interval.start <= end && node.interval.end >= start) {
            result.add(node.interval);
        }

        // If left child's maxEnd is greater than or equal to start, search left subtree
        if (node.left != null && node.left.maxEnd >= start) {
            findOverlappingIntervals(node.left, start, end, result);
        }

        // If right child exists and node's interval does not entirely overlap with [start, end]
        if (node.right != null && node.interval.start <= end) {
            findOverlappingIntervals(node.right, start, end, result);
        }
    }
}
public class IntervalTreeDemo {
    public static void main(String[] args) {
        IntervalTree intervalTree = new IntervalTree();

        intervalTree.insertInterval(15, 20);
        intervalTree.insertInterval(10, 30);
        intervalTree.insertInterval(5, 12);
        intervalTree.insertInterval(25, 35);

        // Find overlapping intervals with [14, 22]
        List<Interval> result = intervalTree.findOverlappingIntervals(14, 22);
        for (Interval interval : result) {
            System.out.println("[" + interval.start + ", " + interval.end + "]");
        }

        // Delete [10, 30] interval
        intervalTree.deleteInterval(10, 30);

        // Find overlapping intervals with [14, 22] after deletion
        result = intervalTree.findOverlappingIntervals(14, 22);
        for (Interval interval : result) {
            System.out.println("[" + interval.start + ", " + interval.end + "]");
        }
    }
}
