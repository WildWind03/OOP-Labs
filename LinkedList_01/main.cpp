#include "LinkedList.cpp"
#include <gtest/gtest.h>

using namespace std;

TEST (LinkedList, constructor_)
{
    LinkedList my_list;
    EXPECT_EQ (my_list.size(), 0);
    EXPECT_EQ (my_list.front(), my_list.back());
}

TEST (LinkedList, front_const_)
{
    const int a1 = 1;
    const int a2 = 5;
    LinkedList my_list;
    EXPECT_EQ (my_list.front(), my_list.back());
    my_list.push_front(a1);
    EXPECT_EQ (my_list.front(), a1);
    my_list.push_front(a2);
    EXPECT_EQ (my_list.front(), a2);
}

TEST (LinkedList, front_)
{
    const int a1 = 1;
    const int a2 = 5;
    LinkedList my_list;
    EXPECT_EQ (my_list.front(), my_list.back());
    my_list.push_front(a1);
    EXPECT_EQ (my_list.front(), a1);
    my_list.push_front(a2);
    EXPECT_EQ (my_list.front(), a2);
}

TEST (LinkedList, back_)
{
    const int a1 = 1;
    const int a2 = 5;
    LinkedList my_list;
    EXPECT_EQ (my_list.front(), my_list.back());
    my_list.push_back(a1);
    EXPECT_EQ (my_list.back(), a1);
    my_list.push_back(a2);
    EXPECT_EQ (my_list.back(), a2);
}

TEST (LinkedList, push_back_)
{
    const int a1 = 1;
    const int a2 = 2;
    const int a3 = 3;
    LinkedList my_list;
    my_list.push_back(a1);
    EXPECT_EQ (my_list.back(), a1);
    EXPECT_EQ (my_list.front(), a1);
    EXPECT_EQ (my_list.size(), 1);
    my_list.push_back(a2);
    EXPECT_EQ (my_list.size(), 2);
    EXPECT_EQ(my_list.front(), a1);
    EXPECT_EQ (my_list.back(), a2);
    my_list.push_back(a3);
    EXPECT_EQ (my_list.back(), a3);
    EXPECT_EQ (my_list.front(), a1);
}

TEST (LinkedList, push_front_)
{
    const int a1 = 1;
    const int a2 = 2;
    const int a3 = 3;
    LinkedList my_list;
    my_list.push_front(a1);
    EXPECT_EQ (my_list.back(), a1);
    EXPECT_EQ (my_list.front(), a1);
    EXPECT_EQ (my_list.size(), 1);
    my_list.push_front(a2);
    EXPECT_EQ (my_list.size(), 2);
    EXPECT_EQ(my_list.front(), a2);
    EXPECT_EQ (my_list.back(), a1);
    my_list.push_front(a3);
    EXPECT_EQ (my_list.front(), a3);
    EXPECT_EQ (my_list.back(), a1);
}

TEST (LinkedList, begin_)
{
    const int a1 = 1;
    const int a2 = 2;
    LinkedList my_list;
    LinkedList::iterator my_iter = my_list.begin();
    EXPECT_EQ (*my_iter, my_list.front());
    my_list.push_front(a1);
    my_iter = my_list.begin();
    EXPECT_EQ (*my_iter, a1);
    my_list.push_front(a2);

    EXPECT_EQ (*my_iter, a1);
}

TEST (LinkedList, end_)
{
    const int a1 = 1;
    const int a2 = 2;
    LinkedList my_list;
    LinkedList::iterator my_iter = my_list.end();
    EXPECT_EQ (*my_iter, my_list.back());
    my_list.push_back(a1);
    my_iter = my_list.end();
    my_iter--;

    EXPECT_EQ (*my_iter, a1);
    my_list.push_back(a2);
    EXPECT_EQ (*my_iter, a1);
}
TEST (LinkedList, copy_constructor_)
{
    const int count_of_testing_values = 10;
    LinkedList my_list;
    LinkedList copy_of_my_list(my_list);
    EXPECT_EQ (my_list.front(), copy_of_my_list.front());
    for (size_t i = 0; i < count_of_testing_values; i++)
    {
        my_list.push_back(i);
    }
    LinkedList copy2_of_my_list(my_list);
    LinkedList::const_iterator iter (my_list.begin());
    LinkedList::const_iterator copy_iter (copy2_of_my_list.begin());
    EXPECT_EQ (*iter, *copy_iter);
    for (size_t i = 1; i < count_of_testing_values; i++)
    {
        iter++;
        copy_iter++;
        EXPECT_EQ (*iter, *copy_iter);
    }
    EXPECT_EQ (my_list.size(), copy2_of_my_list.size());
}

TEST (LinkedList, erase_one_)
{
    LinkedList my_list;
    LinkedList::iterator iter = my_list.begin();
    LinkedList new_list(my_list);
    my_list.erase(iter);
    EXPECT_EQ (my_list, new_list);
    const int count_of_testing_values = 10;
    for (int i = 0; i < count_of_testing_values; i++)
    {
        my_list.push_back(i);
    }
    iter = my_list.begin();

    EXPECT_EQ (my_list.front(), 0);
    iter = my_list.erase(iter);
    EXPECT_EQ (*iter, 1);

    EXPECT_EQ (my_list.front(), 1);
    iter++;

    EXPECT_EQ (*iter, 2);
    iter = my_list.erase(iter);
    EXPECT_EQ (3, *iter);
    EXPECT_EQ (1, *(--iter));
    iter = my_list.end();
    iter--;

    EXPECT_EQ (9, *iter);
    my_list.erase(iter);
    iter = my_list.end();
    iter--;
    EXPECT_EQ (8, *iter);
}

TEST (LinkedList, erase_interval_)
{
    LinkedList my_list;

    EXPECT_EQ(my_list.end(), my_list.begin());
    my_list.erase(my_list.begin(), my_list.end());
    EXPECT_EQ (my_list.front(), my_list.back());
    const int my_count = 10;
    for (int i = 0; i < my_count; i++)
    {
        my_list.push_back(i);
    }
    LinkedList my_list2 (my_list);
    my_list.erase(my_list.begin(), my_list.end());

    EXPECT_TRUE (my_list.empty());
    LinkedList::iterator iter_start;
    LinkedList::iterator iter_end;
    EXPECT_FALSE (my_list2.empty());
    iter_start = my_list2.begin();
    iter_end = my_list2.end();
    --(--(--iter_end));

    EXPECT_EQ (*iter_end, 7);
    my_list2.erase (iter_start, iter_end);
    EXPECT_EQ (my_list2.size(), 3);

    EXPECT_EQ (my_list2.front(), 7);
}

TEST (LinkedList, clear_)
{
    LinkedList my_list;
    const int count_of_testing_values = 10;
    for (int i = 0; i < count_of_testing_values; i++)
    {
        my_list.push_back(i);
    }
    EXPECT_FALSE (my_list.empty());
    my_list.clear();

    EXPECT_TRUE (my_list.empty());
}

TEST (LinkedList, remove_one_)
{
    LinkedList my_list;
    bool is_removed = my_list.remove_one(1);
    EXPECT_FALSE (is_removed);

    EXPECT_TRUE (my_list.empty());
    const int a1 = 5;
    my_list.push_back (a1);
    is_removed = my_list.remove_one(a1);
    EXPECT_TRUE (is_removed);
    EXPECT_TRUE (my_list.empty());
    const int count_of_iterations = 10;
    const int a2 = 2;
    for (int i = 0; i < count_of_iterations; i++)
    {
        if (0 == i%2) my_list.push_back(a1);
        else my_list.push_back(a2);
    }
    my_list.remove_one(a2);

    EXPECT_EQ (my_list.size(), 9);
    EXPECT_EQ (*(my_list.begin()), a1);

    EXPECT_EQ (*(++my_list.begin()), a1);
}

TEST (LinkedList, remove_all_)
{
    LinkedList my_list;
    const int count_of_iterations = 10;
    const int a1 = 1;
    const int a2 = 2;
    size_t freq = my_list.remove_all(a1);
    EXPECT_EQ (freq, 0);
    for (size_t i = 0; i < count_of_iterations; i++)
    {
        if (0 == i%2) my_list.push_back(a1);
        else my_list.push_back(a2);
    }
    freq = my_list.remove_all (a2);

    EXPECT_EQ (my_list.size(), 5);
    EXPECT_EQ (freq, 5);

    EXPECT_FALSE (my_list.contains(a2));
}

TEST (LinkedList, contains_)
{
   const int a1 = 90;
   const int a2 = 45;
   LinkedList my_list;
   EXPECT_FALSE (my_list.contains(a1));
   my_list.push_back (a1);
   EXPECT_TRUE (my_list.contains(a1));
   EXPECT_FALSE (my_list.contains(a2));
}

TEST (LinkedList, count_)
{
   const int a1 = 90;
   const int a2 = 45;
   const int a3 = 30;
   LinkedList my_list;
   EXPECT_EQ (my_list.count(a1), 0);
   my_list.push_back (a1);

   EXPECT_EQ (my_list.count(a1), 1);
   my_list.push_back (a2);
   my_list.push_back (a3);
   EXPECT_EQ (my_list.count(a1), 1);
   my_list.push_back (a1);

   EXPECT_EQ (my_list.count(a1), 2);
   my_list.remove_all(a1);
   EXPECT_EQ (my_list.count(a1), 0);
}

TEST (LinkedList, size_)
{
    const int a1 = 1;
    LinkedList my_list;

    EXPECT_EQ (my_list.size(), 0);
    my_list.push_back(a1);
    EXPECT_EQ (my_list.size(), 1);
    my_list.push_front(a1);

    EXPECT_EQ (my_list.size(), 2);
    my_list.pop_back();
    EXPECT_EQ (my_list.size(), 1);
    my_list.pop_front();

    EXPECT_EQ (my_list.size(), 0);
}

TEST (LinkedList, empty_)
{
    LinkedList my_list;
    EXPECT_TRUE (my_list.empty());
    const int a1 = 1;
    my_list.push_back(a1);
    EXPECT_FALSE (my_list.empty());
    my_list.clear();

    EXPECT_TRUE (my_list.empty());
}

TEST (LinkedList, pop_back_)
{
    LinkedList my_list;
    size_t size1 = my_list.size();
    my_list.pop_back();
    EXPECT_EQ (my_list.size(), size1);
    const int a1 = 1;
    const int a2 = 2;
    my_list.push_back(a1);
    my_list.push_back(a2);
    my_list.pop_back();

    EXPECT_EQ (my_list.back(), a1);
    EXPECT_EQ (my_list.size(), 1);
    my_list.pop_back();

    EXPECT_TRUE (my_list.empty());
}

TEST (LinkedList, pop_front_)
{
    LinkedList my_list;
    size_t size1 = my_list.size();
    my_list.pop_front();
    EXPECT_EQ (my_list.size(), size1);
    const int a1 = 1;
    const int a2 = 2;
    my_list.push_front(a1);
    my_list.push_front(a2);
    my_list.pop_front();

    EXPECT_EQ (my_list.size(), 1);
    EXPECT_EQ (my_list.front(), a1);
    my_list.pop_front();

    EXPECT_TRUE (my_list.empty());
}

TEST (iterator, iterator_copy_)
{
    const int a1 = 1;
    const int a2 = 2;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    LinkedList::iterator iter (my_list.begin());
    LinkedList::iterator iter1 (iter);
    EXPECT_EQ (iter, iter1);
}

TEST (iterator, node_construct_)
{
    const int a1 = 1;
    const int a2 = 2;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    LinkedList::iterator iter (my_list.begin());

    EXPECT_EQ (*iter, my_list.front());
}

TEST (iterator, operator_eq_)
{
    const int a1 = 1;
    const int a2 = 2;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    LinkedList::iterator iter (my_list.begin());
    LinkedList::iterator iter1;
    iter1 = iter;
    EXPECT_EQ (iter, iter1);
}

TEST (iterator, operator_star_)
{
    const int a1 = 1;
    const int a2 = 2;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    LinkedList::iterator iter (my_list.begin());

    EXPECT_EQ (*iter, a1);
}

TEST (iterator, operator_arrow_)
{
    const int a1 = 1;
    const int a2 = 2;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    LinkedList::iterator iter (my_list.begin());

    EXPECT_EQ (*iter.operator->(), a1);
}

TEST (iterator, plus_plus_operator_)
{
    const int a1 = 1;
    const int a2 = 2;
    const int a3 = 3;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    my_list.push_back(a3);
    LinkedList::iterator iter (my_list.begin());
    EXPECT_EQ (*iter, a1);
    EXPECT_EQ (*(++(++iter)), a3);
    ASSERT_THROW (++iter, std::range_error);
}

TEST (iterator, iterator_plus_plus_)
{
    const int a1 = 1;
    const int a2 = 2;
    const int a3 = 3;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    my_list.push_back(a3);
    LinkedList::iterator iter (my_list.begin());
    EXPECT_EQ (*iter, a1);
    iter++;
    iter++;
    EXPECT_EQ (*iter, a3);
    ASSERT_THROW (iter++, std::range_error);
}

TEST (iterator, minus_minus_iterator_)
{
    const int a1 = 1;
    const int a2 = 2;
    const int a3 = 3;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    my_list.push_back(a3);
    LinkedList::iterator iter (my_list.end());
    EXPECT_EQ (*(--iter), a3);
    EXPECT_EQ (*(--(--iter)), a1);
    ASSERT_THROW (--iter, std::range_error);
}

TEST (iterator, iterator_minus_minus_)
{
    const int a1 = 1;
    const int a2 = 2;
    const int a3 = 3;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    my_list.push_back(a3);
    LinkedList::iterator iter (my_list.end());
    iter--;
    EXPECT_EQ (*iter, a3);
    iter--;
    iter--;
    EXPECT_EQ ((*iter), a1);
    ASSERT_THROW (iter--, std::range_error);
}

TEST (const_iterator, iterator_copy_)
{
    const int a1 = 1;
    const int a2 = 2;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    LinkedList::const_iterator iter (my_list.begin());
    LinkedList::const_iterator iter1 (iter);
    EXPECT_EQ (iter, iter1);
}

TEST (const_iterator, node_construct_)
{
    const int a1 = 1;
    const int a2 = 2;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    LinkedList::const_iterator iter (my_list.begin());

    EXPECT_EQ (*iter, my_list.front());
}

TEST (const_iterator, operator_eq_)
{
    const int a1 = 1;
    const int a2 = 2;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    LinkedList::const_iterator iter (my_list.begin());
    LinkedList::const_iterator iter1;
    iter1 = iter;
    EXPECT_EQ (iter, iter1);
}

TEST (const_iterator, operator_star_)
{
    const int a1 = 1;
    const int a2 = 2;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    LinkedList::const_iterator iter (my_list.begin());

    EXPECT_EQ (*iter, a1);
}

TEST (const_iterator, operator_arrow_)
{
    const int a1 = 1;
    const int a2 = 2;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    LinkedList::const_iterator iter (my_list.begin());
    EXPECT_EQ (*iter.operator->(), a1);
}

TEST (const_iterator, plus_plus_operator_)
{
    const int a1 = 1;
    const int a2 = 2;
    const int a3 = 3;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    my_list.push_back(a3);
    LinkedList::const_iterator iter (my_list.begin());
    EXPECT_EQ (*iter, a1);
    EXPECT_EQ (*(++(++iter)), a3);
    ASSERT_THROW (++iter, std::range_error);
}

TEST (const_iterator, iterator_plus_plus_)
{
    const int a1 = 1;
    const int a2 = 2;
    const int a3 = 3;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    my_list.push_back(a3);
    LinkedList::const_iterator iter (my_list.begin());
    EXPECT_EQ (*iter, a1);
    iter++;
    iter++;
    EXPECT_EQ (*iter, a3);
    ASSERT_THROW (iter++, std::range_error);
}

TEST (const_iterator, minus_minus_iterator_)
{
    const int a1 = 1;
    const int a2 = 2;
    const int a3 = 3;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    my_list.push_back(a3);
    LinkedList::const_iterator iter (my_list.end());
    EXPECT_EQ (*(--iter), a3);
    EXPECT_EQ (*(--(--iter)), a1);
    ASSERT_THROW (--iter, std::range_error);
}

TEST (const_iterator, iterator_minus_minus_)
{
    const int a1 = 1;
    const int a2 = 2;
    const int a3 = 3;
    LinkedList my_list;
    my_list.push_back(a1);
    my_list.push_back(a2);
    my_list.push_back(a3);
    LinkedList::const_iterator iter (my_list.end());
    iter--;
    EXPECT_EQ (*iter, a3);
    iter--;
    iter--;
    EXPECT_EQ ((*iter), a1);
    ASSERT_THROW (iter--, std::range_error);
}
//TEST (iterator, )

int main(int argc, char **argv)
{
    const int a1 = 1;
    const int a2 = 5;
    ::testing::InitGoogleTest(&argc, argv);
    std::cout << RUN_ALL_TESTS() << std::endl;
    return 0;
}
