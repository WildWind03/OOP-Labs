#include "LinkedList.h"
#include <gtest/gtest.h>
#include <utility>

using namespace std;

class LinkedListTest : public ::testing::Test
{
public:
    
    LinkedList my_list = LinkedList();
    const int a1 = 1;
    const int a2 = 2;
    const int a3 = 3;
    const int a4 = 4;
    size_t count = 10;
    virtual void SetUp()
    {
        my_list.push_back(a1);
        my_list.push_back(a2);
        my_list.push_back(a3);
    }
};

TEST_F (LinkedListTest, constructor_)
{
    LinkedList list1;
    EXPECT_EQ (list1.size(), 0);
}

TEST_F (LinkedListTest, front_)
{
    EXPECT_EQ (my_list.front(), a1);
    my_list.pop_front();
    EXPECT_EQ (my_list.front(), a2);
    my_list.clear();
    EXPECT_ANY_THROW (my_list.front());
}

TEST_F (LinkedListTest, back_)
{
    EXPECT_EQ (my_list.back(), a3);
    my_list.pop_back();
    EXPECT_EQ (my_list.back(), a2);
    my_list.clear();
    EXPECT_ANY_THROW (my_list.back());
}

TEST_F (LinkedListTest, front_const_)
{
    const LinkedList list2(my_list);
    EXPECT_EQ (list2.front(), a1);
    my_list.clear();
    const LinkedList list1(my_list);
    EXPECT_ANY_THROW(list1.front());
}


TEST_F (LinkedListTest, back_const_)
{
    const LinkedList list2 (my_list);
    EXPECT_EQ (list2.back(), a3);
    my_list.clear();
    const LinkedList list1(my_list);
    EXPECT_ANY_THROW(list1.back());
}

TEST_F (LinkedListTest, push_back_)
{
    EXPECT_EQ (my_list.size(), 3);
    EXPECT_EQ (my_list.front(), a1);
    EXPECT_EQ (my_list.back(), a3);
}

TEST_F (LinkedListTest, push_front_)
{
    my_list.clear();
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



TEST_F (LinkedListTest, begin_)
{
    LinkedList::const_iterator my_iter = my_list.begin();
    EXPECT_EQ (*my_iter, a1);
    EXPECT_EQ (*(++my_iter), a2);
    EXPECT_EQ (*(++my_iter), a3);
    my_list.clear();
    EXPECT_ANY_THROW(my_list.begin());
}


TEST_F (LinkedListTest, end_)
{
    LinkedList::const_iterator my_iter = my_list.end();
    EXPECT_EQ (*(--my_iter), a3);
    EXPECT_EQ (*(--my_iter), a2);
    EXPECT_EQ (*(--my_iter), a1);
    my_list.clear();
    EXPECT_ANY_THROW(my_list.end());
}

TEST_F (LinkedListTest, begin_const_)
{
    const LinkedList list2(my_list);
    LinkedList::iterator my_iter (list2.begin());
    EXPECT_EQ (*my_iter, a1);
    EXPECT_EQ (*(++my_iter), a2);
    const LinkedList list1;
    EXPECT_ANY_THROW(list1.begin());
}

TEST_F (LinkedListTest, end_const_)
{
    const LinkedList list2(my_list);
    LinkedList::iterator my_iter (list2.end());
    EXPECT_EQ (a3, *(--my_iter));
    EXPECT_EQ (a2, *(--my_iter));
    const LinkedList list1;
    EXPECT_ANY_THROW(list1.end());
}

TEST_F (LinkedListTest, cbegin_)
{
    const LinkedList list2(my_list);
    LinkedList::iterator my_iter (list2.cbegin());
    EXPECT_EQ (*my_iter, a1);
    EXPECT_EQ (*(++my_iter), a2);
    const LinkedList list1;
    EXPECT_ANY_THROW(list1.cbegin());
}

TEST_F (LinkedListTest, cend_)
{
    const LinkedList list2(my_list);
    LinkedList::iterator my_iter (list2.cend());
    EXPECT_EQ (a3, *(--my_iter));
    EXPECT_EQ (a2, *(--my_iter));
    const LinkedList list1;
    EXPECT_ANY_THROW(list1.cend());
}

TEST_F (LinkedListTest, copy_constructor_)
{
    LinkedList copy_of_my_list(my_list);
    EXPECT_TRUE (my_list == copy_of_my_list);
    my_list.clear();
    LinkedList copy2_of_my_list(my_list);
    EXPECT_TRUE (copy2_of_my_list == my_list);
    for (size_t i = 0; i < 51; i++)
    {
        my_list.push_back(i);
    }
    EXPECT_ANY_THROW (LinkedList cpy(my_list));
}

TEST_F (LinkedListTest, move_construct_)
{
    LinkedList copy_of_my_list (std::move(my_list));
    EXPECT_EQ (copy_of_my_list.front(), a1);
    EXPECT_EQ (copy_of_my_list.back(), a3);
}

TEST_F (LinkedListTest, erase_one_)
{
    LinkedList::iterator iter = my_list.begin();
    LinkedList new_list(my_list);
    my_list.erase(iter);
    EXPECT_EQ (my_list.front(), a2);
    EXPECT_ANY_THROW(my_list.erase(my_list.end()));
    my_list.clear();
    for (size_t i = 0; i < count; i++)
    {
        my_list.push_back(i);
    }
    iter = my_list.begin();
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
    --iter;
    --iter;
    iter = my_list.erase(iter);
    EXPECT_EQ (*iter, 7);
    --iter;
    EXPECT_EQ (*iter, 5);
}

TEST_F (LinkedListTest, erase_interval_)
{
    my_list.clear();
    for (size_t i = 0; i < count; i++)
    {
        my_list.push_back(i);
    }
    LinkedList my_list2 (my_list);
    my_list.erase(my_list.begin(), my_list.end());
    EXPECT_TRUE (my_list.empty());
    LinkedList::iterator iter_start = my_list2.begin();
    LinkedList::iterator iter_end = my_list2.end();
    --(--(--iter_end));
    EXPECT_EQ (*iter_end, 7);
    my_list2.erase (iter_start, iter_end);
    EXPECT_EQ (my_list2.size(), 3);
    EXPECT_EQ (my_list2.front(), 7);
    EXPECT_EQ (my_list2.back(), 9);
}

TEST_F (LinkedListTest, clear_)
{
    LinkedList list1;
    for (size_t i = 0; i < count; i++)
    {
        list1.push_back(i);
    }
    EXPECT_FALSE (list1.empty());
    list1.clear();
    EXPECT_TRUE (list1.empty());
}

TEST_F (LinkedListTest, remove_one_)
{
    my_list.clear();
    EXPECT_FALSE (my_list.remove_one(1));
    EXPECT_TRUE (my_list.empty());
    my_list.push_back (a1);
    EXPECT_TRUE (my_list.remove_one(a1));
    EXPECT_TRUE (my_list.empty());
    for (size_t i = 0; i < count; i++)
    {
        if (0 == i%2) my_list.push_back(a1);
        else my_list.push_back(a2);
    }
    EXPECT_FALSE (my_list.remove_one(a3));
    my_list.remove_one(a2);
    EXPECT_EQ (my_list.size(), 9);
    EXPECT_EQ (*(my_list.begin()), a1);
    EXPECT_EQ (*(++my_list.begin()), a1);
}

TEST_F (LinkedListTest, remove_all_)
{
    my_list.clear();
    size_t freq = my_list.remove_all(a1);
    EXPECT_EQ (freq, 0);
    for (size_t i = 0; i < count; i++)
    {
        if (0 == i%2) my_list.push_back(a1);
        else my_list.push_back(a2);
    }
    freq = my_list.remove_all (a2);
    EXPECT_EQ (my_list.size(), 5);
    EXPECT_EQ (freq, 5);
    EXPECT_FALSE (my_list.contains(a2));
}

TEST_F (LinkedListTest, contains_)
{
   EXPECT_TRUE (my_list.contains(a1));
   EXPECT_TRUE (my_list.contains(a2));
   EXPECT_TRUE (my_list.contains(a3));
   my_list.pop_back();
   EXPECT_FALSE (my_list.contains(a3));
   my_list.clear();
   EXPECT_FALSE (my_list.contains(a1));
}

TEST_F (LinkedListTest, count_)
{
   EXPECT_EQ (my_list.count(a1), 1);
   EXPECT_EQ (my_list.count(a2), 1);
   my_list.push_back (a1);
   EXPECT_EQ (my_list.count(a1), 2);
   EXPECT_EQ (my_list.count(a4), 0);
   my_list.remove_all(a1);
   EXPECT_EQ (my_list.count(a1), 0);
   my_list.clear();
   EXPECT_EQ (my_list.count(a2), 0);
}

TEST_F (LinkedListTest, size_)
{
    EXPECT_EQ (my_list.size(), 3);
    my_list.push_back(a1);
    EXPECT_EQ (my_list.size(), 4);
    my_list.pop_back();
    EXPECT_EQ (my_list.size(), 3);
    my_list.clear();
    EXPECT_EQ (my_list.size(), 0);
}

TEST_F (LinkedListTest, empty_)
{
    EXPECT_FALSE (my_list.empty());
    my_list.clear();
    EXPECT_TRUE (my_list.empty());
    my_list.push_back(a1);
    EXPECT_FALSE (my_list.empty());
}


TEST_F (LinkedListTest, pop_back_)
{
    my_list.pop_back();
    EXPECT_EQ (my_list.back(), a2);
    EXPECT_EQ (my_list.size(), 2);
    my_list.pop_back();
    EXPECT_EQ (my_list.back(), a1);
    my_list.pop_back();
    EXPECT_TRUE (my_list.empty());
    EXPECT_ANY_THROW(my_list.pop_back());
}

TEST_F (LinkedListTest, pop_front_)
{
    my_list.pop_front();
    EXPECT_EQ (my_list.front(), a2);
    EXPECT_EQ (my_list.size(), 2);
    my_list.pop_front();
    EXPECT_EQ (my_list.front(), a3);
    my_list.pop_front();
    EXPECT_TRUE (my_list.empty());
    EXPECT_ANY_THROW(my_list.pop_front());
}

TEST_F (LinkedListTest, insert_)
{
    LinkedList::iterator iter = my_list.begin();
    iter = my_list.insert (++iter, a4);
    EXPECT_EQ (*iter, a4);
    EXPECT_EQ (*(++iter), a2);
    //push_back and push_front have already checked
}

TEST_F (LinkedListTest, operator_not_equal_)
{
    LinkedList list2;
    EXPECT_TRUE (list2 != my_list);
    list2.push_back (a1);
    EXPECT_TRUE (list2 != my_list);
    list2.push_back (a2);
    list2.push_back (a3);
    EXPECT_FALSE (list2 != my_list);
    list2.pop_back();
    list2.push_back (a4);
    EXPECT_TRUE (list2 != my_list);
    list2.clear();
    my_list.clear();
    EXPECT_FALSE (list2 != my_list);
}

TEST_F (LinkedListTest, operator_equal_)
{
    LinkedList list2;
    EXPECT_FALSE (list2 == my_list);
    list2.push_back (a2);
    list2.push_back (a2);
    list2.push_back (a2);
    EXPECT_FALSE (list2 == my_list);
    list2.clear();
    list2.push_back (a1);
    EXPECT_FALSE (list2 == my_list);
    list2.push_back (a2);
    list2.push_back (a3);
    EXPECT_TRUE (list2 == my_list);
    list2.pop_back();
    list2.push_back (a4);
    EXPECT_FALSE (list2 == my_list);
    list2.clear();
    EXPECT_FALSE (my_list == list2);
    my_list.clear();
    EXPECT_TRUE (list2 == my_list);
}

TEST_F (LinkedListTest, operator_plus)
{
    LinkedList list2;
    LinkedList list3;
    LinkedList list4;
    list4 = list2 + list3; // 0 0
    EXPECT_TRUE (list4 == list2);
    list4 = my_list + list2; // 1 0
    EXPECT_TRUE (list4 == my_list);
    list4 = list2 + my_list;
    EXPECT_TRUE (list4 == my_list);
    list2.push_back(a4);
    list2.push_back(a3);
    list2.push_back(a2);
    list2.push_back(a1);
    list3 = my_list + list2;
    EXPECT_EQ (list3.size(), list2.size() + my_list.size());
    LinkedList::const_iterator iter = list3.begin();
    LinkedList::const_iterator iter1 = my_list.begin();
    LinkedList::const_iterator iter2 = list2.begin();
    EXPECT_EQ (*iter, *iter1);
    for (size_t i = 1; i < my_list.size(); i++)
    {
        EXPECT_EQ (*(++iter), *(++iter1));
    }
    EXPECT_EQ (*(++iter), *iter2);
    for (size_t i = 1; i < list2.size(); i++)
    {
        EXPECT_EQ (*(++iter), *(++iter2));
    }
}

TEST_F (LinkedListTest, operator_eq_plus)
{
    LinkedList list1;
    LinkedList list2;
    list1+=list2;
    EXPECT_TRUE (list1 == list2);
    list1+=my_list;
    EXPECT_TRUE (list1 == my_list);
    my_list+=list2;
    EXPECT_TRUE (my_list == list1);
    list1.clear();
    list1.push_back(a4);
    list1.push_back(a3);
    list1.push_back(a2);
    list1.push_back(a1);
    list2 = list1 + my_list;
    list1+=my_list;
    EXPECT_TRUE (list1 == list2);
}

TEST_F (LinkedListTest, operator_eq_plus_value)
{
    LinkedList list1;
    list1+=a1;
    list1+=a2;
    list1+=a3;
    EXPECT_TRUE (list1 == my_list);
}

TEST_F (LinkedListTest, operator_eq)
{
    LinkedList list1;
    list1 = my_list;
    EXPECT_TRUE (list1 == my_list);
    for (size_t i = 0; i < count; i++)
    {
        list1.push_back (i);
    }
    EXPECT_FALSE (list1 == my_list);
    my_list = list1;
    my_list = my_list;
    EXPECT_TRUE(my_list == list1);
}

TEST_F (LinkedListTest, ITERATOR_copy_)
{
    LinkedList::iterator iter (my_list.begin());
    LinkedList::iterator iter1 (iter);
    EXPECT_TRUE (iter == iter1);
}


TEST_F (LinkedListTest, ITERATOR_constructor_from_node_)
{
    LinkedList::iterator iter (my_list.begin());
    EXPECT_EQ (*iter, my_list.front());
}

TEST_F (LinkedListTest, ITERATOR_operator_equal_)
{
    LinkedList::iterator iter (my_list.begin());
    LinkedList::iterator iter1 = iter;
    EXPECT_TRUE (iter == iter1);
}

TEST_F (LinkedListTest, ITERATOR_operator_not_equal_)
{
    LinkedList::iterator iter (my_list.begin());
    LinkedList::iterator iter1 (--my_list.end());
    EXPECT_TRUE (iter != iter1);
}

TEST_F (LinkedListTest, ITERATOR_operator_star_)
{
    LinkedList::iterator iter (my_list.begin());
    EXPECT_EQ (*iter, a1);
    iter = my_list.end();
    EXPECT_ANY_THROW(*iter);
    EXPECT_EQ (*(--iter), a3);
    LinkedList::iterator iter1;
    EXPECT_ANY_THROW(*iter1);
    iter1 = my_list.end();
    EXPECT_ANY_THROW(*iter1);
}

TEST_F (LinkedListTest, ITERATOR_operator_arrow_)
{
    LinkedList::iterator iter (my_list.begin());
    EXPECT_EQ (*(iter.operator->()), a1);
    iter = my_list.end();
    EXPECT_EQ (*((--iter).operator->()), a3);
    LinkedList::iterator iter1;
    EXPECT_ANY_THROW(iter1.operator->());
    iter1 = my_list.end();
    EXPECT_ANY_THROW(iter1.operator->());
}

TEST_F (LinkedListTest, ITERATOR_plus_plus_operator_)
{
    LinkedList::iterator iter (my_list.begin());
    EXPECT_EQ (*iter, a1);
    EXPECT_EQ (*(++(++iter)), a3);
    iter = my_list.end();
    EXPECT_ANY_THROW(++iter);
}

TEST_F (LinkedListTest, ITERATOR_operator_plus_plus_)
{
    LinkedList::iterator iter (my_list.begin());
    EXPECT_EQ (*iter, a1);
    iter++;
    iter++;
    EXPECT_EQ (*iter, a3);
    iter = my_list.end();
    EXPECT_ANY_THROW(iter++);
}

TEST_F (LinkedListTest, ITERATOR_minus_minus_operator_)
{
    LinkedList::iterator iter (my_list.end());
    EXPECT_EQ (*(--iter), a3);
    EXPECT_EQ (*(--(--iter)), a1);
    EXPECT_ANY_THROW(--iter);
}

TEST_F (LinkedListTest, ITERATOR_operator_minus_minus_)
{
    LinkedList::iterator iter (my_list.end());
    iter--;
    EXPECT_EQ (*iter, a3);
    iter--;
    iter--;
    EXPECT_EQ ((*iter), a1);
    EXPECT_ANY_THROW (iter--);
}

TEST_F (LinkedListTest, CONST_ITERATOR_copy_)
{
    LinkedList::const_iterator iter (my_list.begin());
    LinkedList::const_iterator iter1 (iter);
    EXPECT_TRUE (iter == iter1);
}
TEST_F (LinkedListTest, CONST_ITERATOR_constuctor_from_iterator)
{
    LinkedList::iterator iter (my_list.begin());
    LinkedList::const_iterator iter1 (iter);
    EXPECT_TRUE (iter == iter1);
}

TEST_F (LinkedListTest, CONST_ITERATOR_constructor_from_node_)
{
    LinkedList::const_iterator iter;
    iter = my_list.begin();
    EXPECT_EQ (*iter, my_list.front());
}

TEST_F (LinkedListTest, CONST_ITERATOR_operator_equal_)
{
    LinkedList::const_iterator iter (my_list.begin());
    LinkedList::const_iterator iter1 = iter;
    iter1 = iter;
    EXPECT_TRUE (iter == iter1);
}

TEST_F (LinkedListTest, CONST_ITERATOR_operator_not_equal_)
{
    LinkedList::const_iterator iter (my_list.begin());
    LinkedList::const_iterator iter1 (--my_list.end());
    EXPECT_TRUE (iter != iter1);
}

TEST_F (LinkedListTest, CONST_ITERATOR_operator_star_)
{
    LinkedList::const_iterator iter (my_list.begin());
    EXPECT_EQ (*iter, a1);
    iter = my_list.end();
    EXPECT_ANY_THROW(*iter);
    EXPECT_EQ (*(--iter), a3);
    LinkedList::const_iterator iter1;
    EXPECT_ANY_THROW(*iter1);
    iter1 = my_list.end();
    EXPECT_ANY_THROW(*iter1);
}

TEST_F (LinkedListTest, CONST_ITERATOR_operator_arrow_)
{
    LinkedList::const_iterator iter (my_list.begin());
    EXPECT_EQ (*(iter.operator->()), a1);
    iter = my_list.end();
    EXPECT_EQ (*((--iter).operator->()), a3);
    LinkedList::const_iterator iter1;
    EXPECT_ANY_THROW(iter1.operator->());
    iter1 = my_list.end();
    EXPECT_ANY_THROW(iter1.operator->());
}

TEST_F (LinkedListTest, CONST_ITERATOR_plus_plus_operator_)
{
    LinkedList::const_iterator iter (my_list.begin());
    EXPECT_EQ (*iter, a1);
    EXPECT_EQ (*(++(++iter)), a3);
    iter = my_list.end();
    EXPECT_ANY_THROW(++iter);
}

TEST_F (LinkedListTest, CONST_ITERATOR_operator_plus_plus_)
{
    LinkedList::const_iterator iter (my_list.begin());
    EXPECT_EQ (*iter, a1);
    iter++;
    iter++;
    EXPECT_EQ (*iter, a3);
    iter = my_list.end();
    EXPECT_ANY_THROW(iter++);
}

TEST_F (LinkedListTest, CONST_ITERATOR_minus_minus_operator_)
{
    LinkedList::const_iterator iter (my_list.end());
    EXPECT_EQ (*(--iter), a3);
    EXPECT_EQ (*(--(--iter)), a1);
    EXPECT_ANY_THROW (--iter);
}

TEST_F (LinkedListTest, CONST_ITERATOR_operator_minus_minus_)
{
    LinkedList::const_iterator iter (my_list.end());
    iter--;
    EXPECT_EQ (*iter, a3);
    iter--;
    iter--;
    EXPECT_EQ ((*iter), a1);
    EXPECT_ANY_THROW (iter--);
}

int main(int argc, char **argv)
{
    ::testing::InitGoogleTest(&argc, argv);
    std::cout << RUN_ALL_TESTS() << std::endl;
    return 0;
}
