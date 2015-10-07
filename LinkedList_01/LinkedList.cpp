#include <iostream>
#include <cstdio>
#include "LinkedList.h"
#include <stdexcept>
///////////////////////////////////////////BASE_ITERATOR////////////////////////////////////////////////////////////////////
LinkedList::base_iterator::base_iterator(node *my_node)
{
    current_node = my_node;
}

LinkedList::base_iterator::base_iterator()
{
    current_node = nullptr;
}

LinkedList::base_iterator::base_iterator(const base_iterator & other)
{
    current_node = other.current_node;
}

void LinkedList::base_iterator::move_straight()
{
    current_node = current_node -> next;
}

void LinkedList::base_iterator::move_back()
{
    current_node = current_node -> prev;
}

bool LinkedList::base_iterator::operator!=(const base_iterator & other) const
{
    if (current_node == other.current_node) return false;
    else return true;
}

bool LinkedList::base_iterator::operator==(const base_iterator & other) const
{
    return !(operator!=(other));
}

void LinkedList::base_iterator::equate(const base_iterator & other)
{
    current_node = other.current_node;
}

LinkedList::base_iterator::~base_iterator() {}


///////////////////////////////////////////ITERATOR/////////////////////////////////////////////////////////////////////////

LinkedList::iterator::iterator() : base_iterator() {}

LinkedList::iterator::iterator(const iterator & other) : base_iterator(other) {}

LinkedList::iterator::iterator(node *my_node) : base_iterator(my_node) {}

LinkedList::iterator & LinkedList::iterator::operator=(const iterator & other)
{
    equate(other);
    return *this;
}

value_type & LinkedList::iterator::operator*() const
{
    return (*current_node).value;
}

value_type * LinkedList::iterator::operator->() const
{
    return &((*current_node).value);
}

LinkedList::iterator & LinkedList::iterator::operator++()
{
    if (current_node -> is_next_last())
    {
        throw (std::range_error("Iterator is out of LinkedList"));
    }
    move_straight();
    return *this;
}

LinkedList::iterator & LinkedList::iterator::operator--()
{
    if (current_node -> is_prev_last())
    {
        throw (std::range_error("Iterator is out of LinkedList"));
    }
    move_back();
    return *this;
}

LinkedList::iterator LinkedList::iterator::operator++(int a)
{
    if (current_node -> is_next_last())
    {
        throw (std::range_error("Iterator is out of LinkedList"));
    }
    iterator temp (current_node);
    current_node = current_node -> next;
    return temp;
}

LinkedList::iterator LinkedList::iterator::operator--(int b)
{
    if (current_node -> is_prev_last())
    {
        throw (std::range_error("Iterator is out of LinkedList"));
    }
    iterator temp (current_node);
    current_node = current_node -> prev;
    return temp;
}

LinkedList::iterator::~iterator() {}

///////////////////////////////////////////CONST_ITERATOR///////////////////////////////////////////////////////////////////
LinkedList::const_iterator::const_iterator() : base_iterator() {}

LinkedList::const_iterator::const_iterator(const const_iterator & other) : base_iterator(other) {}

LinkedList::const_iterator::const_iterator(node *my_node) : base_iterator(my_node) {}

LinkedList::const_iterator::const_iterator(const iterator & other) : base_iterator(other) {}

LinkedList::const_iterator & LinkedList::const_iterator::operator=(const const_iterator & other)
{
    equate(other);
    return *this;
}

LinkedList::const_iterator & LinkedList::const_iterator::operator=(const iterator & other)
{
    equate(other);
    return *this;
}

const value_type & LinkedList::const_iterator::operator*() const
{
    return (*current_node).value;
}

const value_type * LinkedList::const_iterator::operator->() const
{
    return &((*current_node).value);
}

LinkedList::const_iterator & LinkedList::const_iterator::operator++()
{
    if (current_node -> is_next_last())
    {
        throw (std::range_error("Iterator is out of LinkedList"));
    }
    move_straight();
    return *this;
}

LinkedList::const_iterator & LinkedList::const_iterator::operator--()
{
    if (current_node -> is_prev_last())
    {
        throw (std::range_error("Iterator is out of LinkedList"));
    }
    move_back();
    return *this;
}

LinkedList::const_iterator LinkedList::const_iterator::operator++(int a)
{
    if (current_node -> is_next_last())
    {
        throw (std::range_error("Iterator is out of LinkedList"));
    }
    const_iterator temp(current_node);
    current_node = current_node -> next;
    return temp;
}

LinkedList::const_iterator LinkedList::const_iterator::operator--(int b)
{
    if (current_node -> is_prev_last())
    {
        throw (std::range_error("Iterator is out of LinkedList"));
    }
    const_iterator temp (current_node);
    current_node = current_node -> prev;
    return temp;
}

LinkedList::const_iterator::~const_iterator() {}

///////////////////////////////////////////LINKED_LIST//////////////////////////////////////////////////////////////////////

LinkedList::node* LinkedList::create_new_node()
{
    node *temp;
    temp = new node;
    temp -> next = nullptr;
    temp -> prev = nullptr;
    temp -> value = value_type();
    return temp;
}

LinkedList::LinkedList()
{
    list_size = 0;
    try
    {
        last_node = create_new_node();
    }
    catch (std::bad_alloc error)
    {
        std::cerr << error.what() << std::endl;
        exit(-1);
    }
    first = last_node;
    last = last_node;
}

void LinkedList::copy_list(const LinkedList & other)
{
    LinkedList::const_iterator other_iter = other.begin();
    if (0 != other.size())
    {
        push_back(*other_iter);
    }
    for (size_t i = 1; i < other.size(); i++)
    {
        ++other_iter;
        push_back(*other_iter);
    }
}
LinkedList::LinkedList(const LinkedList & other) : LinkedList()
{
    copy_list(other);
}

LinkedList::~LinkedList()
{
    clear();
    delete(last_node);
}

void LinkedList::clear()
{
    erase(begin(), end());
}

value_type & LinkedList::front()
{
    return first -> value;
}

const value_type & LinkedList::front() const
{
    return first -> value;
}

value_type & LinkedList::back()
{
    return last -> value;
}

const value_type & LinkedList::back() const
{
    return last -> value;
}

LinkedList::iterator LinkedList::begin()
{
    iterator my_iterator(first);
    return my_iterator;
}

const LinkedList::iterator LinkedList::begin() const
{
    return cbegin();
}

const LinkedList::iterator LinkedList::cbegin() const
{
    const iterator my_iterator(first);
    return my_iterator;
}

LinkedList::iterator LinkedList::end()
{
    iterator my_iterator(last_node);
    return my_iterator;
}

const LinkedList::iterator LinkedList::cend() const
{
    const iterator my_iterator(last_node);
    return my_iterator;
}

const LinkedList::iterator LinkedList::end() const
{
    return cend();
}

bool LinkedList::contains(const value_type & value) const
{
    const_iterator iter(begin());
    if (*iter == value) return true;
    for (size_t i = 1; i < size(); i++)
    {
        ++iter;
        if (*iter == value) return true;
    }
    return false;
}

size_t LinkedList::count(const value_type & value) const
{
    size_t freq = 0;
    const_iterator iter(begin());
    if (*iter == value) freq++;
    for (size_t i = 1; i < size(); i++)
    {
	++iter;
        if (*iter == value) freq++;
    }
    return freq;
}

size_t LinkedList::size() const
{
    return list_size;
}

bool LinkedList::empty() const
{
    if(!size()) return true;
    else return false;
}

LinkedList::iterator LinkedList::erase (iterator pos)
{
    node *cur_node = pos.current_node;
    if (cur_node == last_node || pos.current_node == nullptr) return pos;
    node *next_node = pos.current_node -> next;
    node *prev_node = pos.current_node -> prev;
    next_node -> prev = prev_node;
    if (nullptr != prev_node) prev_node -> next = next_node;
    else
    {
        first = next_node;
        first -> prev = nullptr;
    }
    if (last==cur_node)
    {
        if (1 == size()) last = last_node;
        else
        {
            last = prev_node;
        }
    }
    delete(cur_node);
    list_size--;
    return iterator(next_node);
}

LinkedList::iterator LinkedList::erase (iterator begin, iterator end)
{
    while (begin != end)
    {
        begin = erase(begin);
    }
    return begin;
}

size_t LinkedList::remove_all (const value_type & value)
{
    size_t freq = 0;
    while (remove_one(value))
    {
        freq++;
    }
    return freq;
}

bool LinkedList::remove_one(const value_type & value)
{
    if (0 == size()) return false;
    iterator iter = begin();
    if (*iter == value)
    {
        erase(iter);
        return true;
    }
    for (size_t i = 1; i < size(); i++)
    {
	++iter;
        if (*iter == value)
        {
            erase(iter);
            return true;
        }
    }
    return false;
}

void LinkedList::pop_back()
{
    if (0 == size()) return;
    else
    {
        iterator iter = end();
	--iter;
        erase (iter);
    }
}

void LinkedList::pop_front()
{
    if (0 == size()) return;
    else erase (begin());
}

void LinkedList::push_back(const value_type & value)
{
    insert(iterator(last_node), value);
}

void LinkedList::push_front(const value_type & value)
{
    insert(iterator(first), value);
}

LinkedList::iterator LinkedList::insert(iterator before, const value_type & value)
{
    node *back_node = before.current_node -> prev;
    node *front_node = before.current_node;
    node *new_node = create_new_node();
    new_node -> value = value;
    new_node -> prev = back_node;
    new_node -> next = front_node;
    list_size++;
    front_node -> prev = new_node;
    if (front_node==last_node)
    {
        last = new_node;
    }
    if (back_node!=nullptr)
    {
        back_node -> next = new_node;
    }
    else
    {
        first = new_node;
    }
    return --before;
}

bool LinkedList::operator!=(const LinkedList & other) const
{
    return !(other == *this);
}
bool LinkedList::operator==(const LinkedList & other) const
{
    if (other.size() != size()) return false;
    iterator other_iter = other.begin();
    iterator this_iter = begin();
    if (*other_iter != *this_iter) return false;
    for (size_t i = 1; i < size(); i++)
    {
	++other_iter;
	++this_iter;
        if (*other_iter != *this_iter) return false;
    }
    return true;
}
LinkedList LinkedList::operator+(const LinkedList & other) const
{
    LinkedList list2;
    LinkedList::const_iterator iter = begin();
    LinkedList::const_iterator iter1 = other.begin();
    if (0 != size()) list2.push_back(*iter);
    for (size_t i = 1; i < size(); i++)
    {
        list2.push_back(*(++iter));
    }
    if (0 != other.size()) list2.push_back (*iter1);
    for (size_t i = 1; i < other.size(); i++)
    {
        list2.push_back(*(++iter1));
    }
    return list2;
}
LinkedList & LinkedList::operator+=(const LinkedList & other)
{
    LinkedList::const_iterator iter = other.begin();
    if (0 != other.size()) push_back(*iter);
    for (size_t i = 1; i < other.size(); i++)
    {
        push_back(*(++iter));
    }
    return *this;
}
LinkedList & LinkedList::operator+=(const value_type & value)
{
    push_back(value);
    return *this;
}
LinkedList & LinkedList::operator=(const LinkedList & other)
{
    LinkedList *mcpy = new LinkedList;
    try
    {
        mcpy -> copy_list(*this);
    }
    catch (std::bad_alloc error)
    {
	delete (mcpy);
        std::cerr << error.what() << std::endl;
        return *this;
    }
    try
    {
        copy_list(other);
    }
    catch (std::bad_alloc error)
    {
        std::cerr << error.what() << std::endl;
        this -> ~LinkedList();
        this -> first = mcpy -> first;
        this -> last = mcpy -> last;
        this -> last_node = mcpy -> last_node;
	return *this;
    };
    delete(mcpy);
    return *this;
}
