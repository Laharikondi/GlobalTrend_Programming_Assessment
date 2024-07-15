import java.util.Stack;

public class ValidParentheses {

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' && !stack.isEmpty() && stack.peek() == '(') {
                stack.pop();
            } else if (c == '}' && !stack.isEmpty() && stack.peek() == '{') {
                stack.pop();
            } else if (c == ']' && !stack.isEmpty() && stack.peek() == '[') {
                stack.pop();
            } else {
                return false; // Unmatched closing bracket or empty stack
            }
        }
        
        return stack.isEmpty(); // Stack should be empty if all matched
    }

    public static void main(String[] args) {
        ValidParentheses validator = new ValidParentheses();

        // Test cases
        System.out.println(validator.isValid("()"));       // true
        System.out.println(validator.isValid("()[]{}"));   // true
        System.out.println(validator.isValid("(]"));       // false
        System.out.println(validator.isValid("([)]"));     // false
        System.out.println(validator.isValid("{[]}"));     // true
        System.out.println(validator.isValid(""));         // true (empty string is valid)
    }
}
