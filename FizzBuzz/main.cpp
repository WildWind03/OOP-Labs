#include <iostream>

using namespace std;

int main()
{
    const int N = 1988997878984;
    if (N > 1)
    {
        for (int i = 1; i <= N; i++)
        {
            cout << i;
            if ((i%3 == 0) && (i%15 != 0))
            {
                cout << " Fizz";
            }
            if ((i%5 == 0) && (i%15 != 0))
            {
                cout << " Buzz";
            }
            if (i%15 == 0)
            {
                cout << " Fizz-Buzz";
            }
            cout << "\n";
        }
    }
    else
    {
        cout << "N is incorrect";
    }
    return 0;
}
