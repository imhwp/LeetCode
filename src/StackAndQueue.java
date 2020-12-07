import java.util.Stack;

/**
 * @author Wuping HUANG
 * @version 1.0
 * @date 12/6/2020 3:20 PM
 */
public class StackAndQueue {
    /**
     * 使用两个stack实现一个队列
     */
    public static void main(String[] args) {
        StackQueue queue = new StackQueue();
        queue.add(1);
        queue.add(2);
        int res = queue.delete();
         System.out.println(res);
        queue.add(3);
        queue.add(4);
        res = queue.delete();
        System.out.println(res);
        res = queue.delete();
        System.out.println(res);
        res = queue.delete();
        System.out.println(res);
        try{
            res = queue.delete();
            System.out.println(res);
        }catch (Exception e){
            e.printStackTrace();
        }


    }


}

class StackQueue{
    Stack<Integer> addStack;
    Stack<Integer> delStack;

    StackQueue(){
        addStack = new Stack<>();
        delStack = new Stack<>();
    }

    public void add(int a){
        addStack.push(a);
    }
    public int delete(){
        if(delStack.isEmpty()){
            while(!addStack.isEmpty()){
                delStack.push(addStack.pop());
            }
        }
        if(delStack.isEmpty()){
            return -1;
        }
        return delStack.pop();
    }
}
