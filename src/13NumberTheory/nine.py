# Solution for https://open.kattis.com/problems/nine
# With help from https://github.com/amartop
print(*[(8 * pow(9,eval(input())-1,1000000007)%1000000007) for x in range(eval(input()))], sep = "\n")