#include <iostream>
#include <cstdlib>
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
    try
    {
        temp = new node;
    }
    catch (std::bad_alloc)
    {
        std::cerr << "Linked List :: There isn't sufficient memory!" << std::endl;
        exit(-1);
    }
    temp -> next = nullptr;
    temp -> prev = nullptr;
    temp -> value = value_type();
    return temp;
}

LinkedList::LinkedList()
{
    list_size = 0;
    last_node = create_new_node();
    first = last_node;
    last = last_node;
}

void LinkedList::copy_list(const LinkedList & other)
{
    list_size = other.size();
    if (0 == size())
    {
        last_node = create_new_node();
        first = last_node;
        last = last_node;
    }
    else
    {
        LinkedList::const_iterator other_iter = other.begin();
        first = create_new_node();
        first -> value = *other_iter;
        first -> prev = nullptr;
        node *temp_this;
        temp_this = first;
        for (size_t i = 1; i < size(); i++)
        {
            temp_this -> next = create_new_node();
            temp_this -> next -> prev = temp_this;
            temp_this = temp_this -> next;
            try {other_iter++;}
            catch (std::range_error error) {std::cerr << error.what() << std::endl;}
            temp_this -> value = *other_iter;
        }
        temp_this -> next = last_node;
        last = temp_this;
        last_node -> prev = last;
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
    return front(); //абсолютно одинаквые реализации?
}

value_type & LinkedList::back()
{
    return last -> value;
}

const value_type & LinkedList::back() const
{
    return back();
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
        try {iter++;}
        catch (std::range_error error) {std::cerr << error.what() << std::endl;}
        if (*iter == value) return true;
    }
    return false;
}

size_t LinkedList::count(const value_type & value) const
{
    size_t freq = 0;
    const_iterator iter(begin());
    for (size_t i = 0; i < size(); i++)
    {
        if (*iter == value) freq++;
        try {iter++;}
        catch (std::range_error error) {std::cerr << error.what() << std::endl;}
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

LinkedList::iterator LinkedList::erase (iterator pos) //дружественный класс
{
    node *cur_node = pos.current_node; //last_node
    if (cur_node == last_node) return pos;
    node *next_node = pos.current_node -> next; // !=nullptr
    node *prev_node = pos.current_node -> prev;
    next_node -> prev = prev_node;
    if (nullptr != prev_node) prev_node -> next = next_node;
    else first = next_node;
    if (last==cur_node)
    {
        if (1 == size()) last = last_node;
        else
        {
            last = prev_node;
        }
    }
    delete(&cur_node);
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
    if (size() == 0) return false;
    iterator iter = begin(); //почему не работает iter = (); iter = begin();
    if (*iter == value)
    {
        erase(iter);
        return true;
    }
    for (size_t i = 1; i < size(); i++)
    {
        try {iter++;}
        catch (std::range_error error) {std::cerr << error.what() << std::endl;}
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
    if (size() == 0) return;
    else
    {
        iterator iter = end();
        try {--iter;}
        catch (std::range_error error) {std::cerr << error.what() << std::endl;}
        erase (iter);
    }
}

void LinkedList::pop_front()
{
    if (size() == 0) return;
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
    node *back_node = before.current_node -> prev; // nullptr node
    node *front_node = before.current_node; // last_node last_node
    node *new_node = create_new_node();
    new_node -> value = value;
    new_node -> prev = back_node; //nullptr node
    new_node -> next = front_node; //last_node last_node
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
    try {--before;}
    catch (std::range_error error) {std::cerr << error.what() << std::endl;}
    return before;
}

bool LinkedList::operator!=(const LinkedList & other) const
{
    if (other==*this) return false;
    return true;
}
bool LinkedList::operator==(const LinkedList & other) const
{
    iterator other_iter = other.begin();
    iterator this_iter = begin();
    for (size_t i = 0; i < size(); i++)
    {
        if (*other_iter != *this_iter) return false;
        try {other_iter++; this_iter++;}
        catch (std::range_error error) {std::cerr << error.what() << std::endl;}
    }
    return true;
}
LinkedList LinkedList::operator+(const LinkedList & other) const
{
    node *this_last = last;
    node *other_first = other.first;
    this_last -> next = other.first;
    other_first -> prev = this_last;
    delete(last_node);
}
LinkedList & LinkedList::operator+=(const LinkedList & other)
{
    *this = *this + other;
    return *this;
}
LinkedList & LinkedList::operator+=(const value_type & value)
{
    push_back(value);
    return *this;
}
LinkedList & LinkedList::operator=(const LinkedList & other)
{
    clear();
    copy_list(other);
    return *this;
}
