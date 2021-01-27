/*
The following iterative sequence is defined for the set of positive integers:    
    n → n/2 (n is even)
    n → 3n + 1 (n is odd)
Using the rule above and starting with 13, we generate the following sequence:
    13 → 40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1
It can be seen that this sequence (starting at 13 and finishing at 1) contains 10 terms.
Although it has not been proved yet (Collatz Problem), it is thought that all starting
numbers finish at 1. Describe an algorithm that will find which starting number, under
one million, produces the longest chain.
*/

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class CollatzProblem {

    public static int collatzProblem() {
        int length = 0, numLongestSeq = 0;
        int[] seqLengths = new int[1000000];
        
        for (int i = 1; i <= 1000000; i++) {
            int localLen = 0;
            int initial = i, curr = i;

            while (curr != 1) {
                if (seqLengths[curr] != 0) {
                    localLen += seqLengths[curr];
                    break;
                } else {
                    if (curr % 2 == 0) {
                        curr /= 2;
                    } else {
                        curr *= 3;
                        curr++;
                    }
                    localLen++;
                }
            }
            seqLengths[initial] = localLen;

            if (localLen > length) {
                numLongestSeq = initial;
            }
        }
        return numLongestSeq; 
    }

    public static void main(String[] args) {
        System.out.println(collatz());
    }
}