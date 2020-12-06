import java.util.Stack;

/**
 * @author Wuping HUANG
 * @version 1.0
 * @date 12/5/2020 10:15 PM
 */
class Node{
    int val;
    Node left;
    Node right;
    Node parent;
    Node(int val){this.val = val;}
}
public class BinaryTree{

    public static void main(String[] args) {
        Node root = new Node(10);
        Node l = new Node(6);
        Node r = new Node(14);
        l.parent = root;
        r.parent = root;
        root.left = l;
        root.right = r;
        Node ll = new Node(4);
        Node lr = new Node(8);
        ll.parent = l;
        lr.parent = l;
        l.left = ll;
        l.right = lr;
        Node rl = new Node(12);
        Node rr = new Node(16);
        rl.parent = r;
        rr.parent = r;
        r.left = rl;
        r.right = rr;
//        traversalPreOrder(root);
//        noTraversalPreOrder(root);
//        noTraversalInOrder(root);
//        NoTraversalPostOrder(root);
        int[] preOrder = {1,2,4,7,3,5,6,8};
        int[] inOrder = {4,7,2,1,5,3,8,6};
        int[] postOrder = {7,4,2,5,8,6,3,1};
//        Node tree = rebuildBinaryTree2(inOrder,postOrder);
//        noTraversalPreOrder(tree);
//        noTraversalInOrder(root);
        Node res = findNextNode(root);
        System.out.println(res.val);

    }

    /**
     * 先序遍历,循环方式
     * 非递归方式，用一个栈存储对应的节点
     * @param root
     */
    public static void noTraversalPreOrder(Node root){
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            Node node = stack.pop();
            System.out.println(node.val);
            if(node.right!=null){
                stack.push(node.right);
            }
            if(node.left!=null){
                stack.push(node.left);
            }
        }
    }
    /**
     * 先序遍历，递归方式
     * 同理，中序遍历和后序只需要调整打印val的位置即可
     */
    public static void traversalPreOrder(Node root){
        if(root == null){
            return;
        }
        System.out.println(root.val);
        traversalPreOrder(root.left);
        traversalPreOrder(root.right);
    }
    /**
     * 非递归方式实现中序遍历
     */
    public static void noTraversalInOrder(Node node){
        Stack<Node> stack = new Stack<>();
        Node cur = node;
        while(cur!=null || !stack.isEmpty()){
            if (cur!=null){
                stack.push(cur);
                cur = cur.left;
            }else {
                cur = stack.pop();
                System.out.println(cur.val);
                cur = cur.right;
            }
        }
    }
    /**
     * 非递归方式实现后序遍历
     */
    public static void noTraversalPostOrder(Node node){
        Stack<Integer> res = new Stack<>();
        Stack<Node> temp = new Stack<>();
        temp.push(node);
        while(!temp.isEmpty()){
            Node cur = temp.pop();
            res.push(cur.val);
            if(cur.left!=null){
                temp.push(cur.left);
            }
            if(cur.right!=null){
                temp.push(cur.right);
            }
        }
        while(!res.isEmpty()){
            Integer pop = res.pop();
            System.out.println(pop);
        }
    }
    /**
     * 重建二叉树
     * 已知前序遍历和中序遍历
     */
    public static Node rebuildBinaryTree(int[] preOrder,int[] inOrder){
        if(preOrder == null || preOrder.length!=inOrder.length){
            return null;
        }
        Node node = new Node(preOrder[0]);
        if(preOrder.length == 1){
            return node;
        }
        int index = 0;
        while(inOrder[index] != preOrder[0]){
            index ++;
        }
        if(index > 0){
            int[] lPrevOrder = new int[index];
            int[] lInOrder = new int[index];
            System.arraycopy(preOrder,1,lPrevOrder,0,index);
            System.arraycopy(inOrder,0,lInOrder,0,index);
            node.left = rebuildBinaryTree(lPrevOrder,lInOrder);
        }
        if(preOrder.length - index -1 > 0){
            int[] rPreOrder = new int[preOrder.length - index -1];
            int[] rInOrder = new int[preOrder.length - index -1];
            System.arraycopy(preOrder,1+ index,rPreOrder,0,preOrder.length-index-1);
            System.arraycopy(inOrder,1+ index,rInOrder,0,preOrder.length-index-1);
            node.right = rebuildBinaryTree(rPreOrder,rInOrder);
        }
        return node;
    }
    /**
     * 根据后续遍历和中序遍历重构二叉树
     */
    public static Node rebuildBinaryTree2(int[] inOrder, int[] postOrder){
        if (inOrder == null || inOrder.length!=postOrder.length){
            return null;
        }
        Node root = new Node(postOrder[postOrder.length-1]);
        if (inOrder.length == 1){
            return root;
        }
        int index = 0;
        while(inOrder[index] != root.val){
            index++;
        }
        if(index > 0){
            int[] lInOrder = new int[index];
            int[] lPostOrder = new int[index];
            System.arraycopy(inOrder,0,lInOrder,0,index);
            System.arraycopy(postOrder,0,lPostOrder,0,index);
            root.left = rebuildBinaryTree2(lInOrder,lPostOrder);
        }
        if(inOrder.length-index-1>0){
            int[] rInOrder = new int[inOrder.length-1-index];
            int[] rPostOrder = new int[inOrder.length-1-index];
            System.arraycopy(inOrder,index+1,rInOrder,0,inOrder.length-1-index);
            System.arraycopy(postOrder,index,rPostOrder,0,inOrder.length-1-index);
            root.right = rebuildBinaryTree2(rInOrder,rPostOrder);
        }
        return root;
    }
    /**
     * 该二叉树结构：有指向父亲节点的指针
     * 中序遍历，已知一个节点，求该节点的下一个节点
     * 1.该节点有右孩子，从右孩子开始一直找左孩子，直到最左一个节点返回
     * 2.该节点是父亲节点的左孩子，父亲节点为答案
     * 3.上述都不满足，则从该节点往上寻找节点，找到一个节点是其父亲节点的左还子，该父亲节点为答案
     */
    public static Node findNextNode(Node root){
        if(root == null){
            return null;
        }
        if (root.right != null){
            Node cur = root.right;
            while(cur.left != null){
                cur = cur.left;
            }
            return cur;
        }
        while(root.parent!=null){
            if (root.parent.left == root){
                return root.parent;
            }
            root = root.parent;
        }
        return null;
    }
}