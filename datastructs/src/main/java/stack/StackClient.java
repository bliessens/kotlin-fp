package stack;

import java.util.List;

public class StackClient {

    private final Stack<String> stack = Stacks.stackOf("a", "b");

    public void printAll() {
        while (!stack.isEmpty()) {
            stack.pop();
            System.out.println(stack.size());
        }
    }

    public <T> Stack<T> listToStack(List<T> list) {
        return Stacks.toStack(list);
    }

}
