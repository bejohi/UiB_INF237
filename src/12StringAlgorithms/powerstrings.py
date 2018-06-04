# Solution for https://open.kattis.com/problems/powerstrings
def solve():
    res_arr = []
    for test_case in range(10):
        input_str = input()
        if input_str == ".":
            break
        str_len = len(input_str)
        for i in range(1, len(input_str)+1):
            if str_len % i != 0:
                continue
            div_value = str_len // i
            if input_str[:i] * div_value == input_str:
                res_arr.append(int(len(input_str) / i))
                break

    for i in res_arr:
        print(i)


if __name__ == "__main__":
solve()