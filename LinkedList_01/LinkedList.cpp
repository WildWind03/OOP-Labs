#include <iostream>
#include <cstdlib>
#include "LinkedList.h"

int main()
{
    return 0;
}
///////////////////////////////////////////BASE_ITERATOR////////////////////////////////////////////////////////////////////

LinkedList::base_iterator::base_iterator(LinkedList::node *my_node)
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
    if (current_node == other.current_node) return true;
    else return false;
}

bool LinkedList::base_iterator::operator==(const base_iterator & other) const
{
    return !operator!=(other);
}

void LinkedList::base_iterator::equate(const base_iterator & other)
{
    current_node = other.current_node;
}

LinkedList::base_iterator::~base_iterator() {}


///////////////////////////////////////////ITERATOR/////////////////////////////////////////////////////////////////////////

LinkedList::iterator::iterator() : LinkedList::base_iterator::base_iterator() {}

LinkedList::iterator::iterator(const iterator & other) : LinkedList::base_iterator::base_iterator(other) {}

LinkedList::iterator::iterator(LinkedList::node *my_node) : LinkedList::base_iterator::base_iterator(my_node) {}

LinkedList::iterator& LinkedList::iterator::operator=(const iterator & other)
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
    move_straight();
    return *this;
}

LinkedList::iterator & LinkedList::iterator::operator--()
{
    move_back();
    return *this;
}

LinkedList::iterator LinkedList::iterator::operator++(int a)
{
    iterator temp;
    temp.current_node = current_node;
    current_node = current_node -> next;
    return temp;
}

LinkedList::iterator LinkedList::iterator::operator--(int b)
{
    iterator temp;
    temp.current_node = current_node;
    current_node = current_node -> prev;
    return temp;
}

LinkedList::iterator::~iterator() {}

///////////////////////////////////////////CONST_ITERATOR///////////////////////////////////////////////////////////////////
LinkedList::const_iterator::const_iterator() : LinkedList::base_iterator::base_iterator() {}

LinkedList::const_iterator::const_iterator(iterator & other) : LinkedList::base_iterator::base_iterator(other) {}

LinkedList::const_iterator::const_iterator(LinkedList::node *my_node) : LinkedList::base_iterator::base_iterator(my_node) {}

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
    move_straight();
    return *this;
}

LinkedList::const_iterator & LinkedList::const_iterator::operator--()
{
    move_back();
    return *this;
}

LinkedList::const_iterator LinkedList::const_iterator::operator++(int a)
{
    const_iterator temp;
    temp.current_node = current_node;
    current_node = current_node -> next;
    return temp;
}

LinkedList::const_iterator LinkedList::const_iterator::operator--(int b)
{
    const_iterator temp;
    temp.current_node = current_node;
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
    return temp;
}

LinkedList::LinkedList()
{
    list_size = 0;
    last_node = create_new_node();
    first = last_node;
    last = last_node;
}


LinkedList::LinkedList(const LinkedList & other)
{
    list_size = other.size();
    if (size() == 0)
    {
        last_node = create_new_node();
        last_node -> next = nullptr;
        last_node -> prev = nullptr;
        last_node -> value = value_type();
        first = last_node;
        last = last_node;
    }
    else
    {
        first = create_new_node();
        first -> value = other.first -> value;
        first -> prev = nullptr;
        node *temp_this, *temp_other;
        temp_this = LinkedList::first;
        temp_other = other.first;
        for (size_t i = 1; i < size(); i++)
        {
            temp_this -> next = create_new_node();
            temp_this -> next -> prev = temp_this;
            temp_this = temp_this -> next;
            temp_other = temp_other -> next;
            temp_this -> value = temp_other -> value;
        }
        temp_this -> next = last_node;
        last = temp_this;
    }
}

LinkedList::~LinkedList()
{
    clear();
    delete(last_node);
}

void LinkedList::clear()
{
    node *temp_next, *temp_current;
    temp_next = first -> next;
    temp_current = first;
    for (size_t i = 0; i < size(); i++)
    {
        delete(temp_current);
        temp_current = temp_next;
        temp_next = temp_next -> next;
    }
    first = last_node;
    last = last_node;
}

value_type & LinkedList::front()
{
    return first -> value;
}

const value_type & LinkedList::front() const
{
    return front(); //‡·ÒÓÎ˛ÚÌÓ Ó‰ËÌ‡Í‚˚Â Â‡ÎËÁ‡ˆËË?
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
    iterator my_iterator(this -> first);
    return my_iterator;
}

const LinkedList::iterator LinkedList::begin() const
{
    const iterator my_iterator(this -> first);
    return my_iterator;
}

const LinkedList::iterator LinkedList::cbegin() const
{
    const iterator my_iterator(this -> first);
    return my_iterator;
}

LinkedList::iterator LinkedList::end()
{
    iterator my_iterator(this -> last);
    return my_iterator;
}

const LinkedList::iterator LinkedList::cend() const
{
    const iterator my_iterator(this -> last);
    return my_iterator;
}

const LinkedList::iterator LinkedList::end() const
{
    const iterator my_iterator(this -> last);
    return my_iterator;
}

bool LinkedList::contains(const value_type & value) const
{
    node *temp;
    temp = first;
    for (size_t i = 0; i < LinkedList::size(); i++)
    {
        if (temp -> value == value) return true;
        temp = temp -> next;
    }
    return false;
}

size_t LinkedList::count(const value_type & value) const
{
    size_t freq = 0;
    node *temp;
    temp = first;
    for (size_t i = 0; i < LinkedList::size(); i++)
    {
        if (temp -> value == value) freq++;
        temp = temp -> next;
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

LinkedList::iterator LinkedList::erase (LinkedList::const_iterator pos) // ¿ Œ… ¿–√”Ã≈Õ“??
{

}




