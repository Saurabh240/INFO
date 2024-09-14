Q-1 WAP to find if a number is prime or not

public class PrimeNumberSimple {

    // Function to check if a number is prime
    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false; // numbers less than or equal to 1 are not prime
        }
        for (int i = 2; i <= Math.sqrt(number); i++) { // loop till square root of the number
            if (number % i == 0) { // if divisible by any number other than 1 and itself
                return false;
            }
        }
        return true;
    }

    // Main method to test the isPrime function
    public static void main(String[] args) {
        int number = 29; // Test with a number

        if (isPrime(number)) {
            System.out.println(number + " is a prime number.");
        } else {
            System.out.println(number + " is not a prime number.");
        }
    }
}
