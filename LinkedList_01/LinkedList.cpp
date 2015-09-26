#include <iostream>
#include <cstdlib>
#include "LinkedList.h"

int main()
{
    return 0;
}
///////////////////////////////////////////BASE_ITERATOR////////////////////////////////////////////////////////////////////

LinkedList::base_iterator::base_iterator()
{
    LinkedList::base_iterator::current_node = nullptr;
}

LinkedList::base_iterator::base_iterator(const base_iterator & other)
{
    LinkedList::base_iterator::current_node = other.current_node;
}

void LinkedList::base_iterator::move_straight()
{
    LinkedList::base_iterator::current_node = LinkedList::base_iterator::current_node -> next;
}

void LinkedList::base_iterator::move_back()
{
    LinkedList::base_iterator::current_node = LinkedList::base_iterator::current_node -> prev;
}

bool LinkedList::base_iterator::operator!=(const base_iterator & other) const
{
    if (LinkedList::base_iterator::current_node == other.current_node) return true;
    else return false;
}

bool LinkedList::base_iterator::operator==(const base_iterator & other) const
{
    return !LinkedList::base_iterator::operator!=(other);
}

void LinkedList::base_iterator::equate(const base_iterator & other)
{
    LinkedList::base_iterator::current_node = other.current_node;
}

LinkedList::base_iterator::~base_iterator() {}


///////////////////////////////////////////ITERATOR/////////////////////////////////////////////////////////////////////////

LinkedList::iterator::iterator() : LinkedList::base_iterator::base_iterator() {}

LinkedList::iterator::iterator(const iterator & other) : LinkedList::base_iterator::base_iterator(other) {}

LinkedList::iterator& LinkedList::iterator::operator=(const iterator & other)
{
    LinkedList::base_iterator::equate(other);
    return *this;
}

value_type & LinkedList::iterator::operator*() const
{
    return (*LinkedList::iterator::current_node).value;
}

value_type * LinkedList::iterator::operator->() const
{
    return &((*LinkedList::iterator::current_node).value);
}

LinkedList::iterator & LinkedList::iterator::operator++()
{
    LinkedList::iterator::move_straight();
    return *this;
}

LinkedList::iterator & LinkedList::iterator::operator--()
{
    LinkedList::iterator::move_back();
    return *this;
}

LinkedList::iterator LinkedList::iterator::operator++(int a)
{
    LinkedList::iterator temp;
    temp.current_node = LinkedList::iterator::current_node;
    LinkedList::iterator::current_node = LinkedList::iterator::current_node -> next;
    return temp;
}

LinkedList::iterator LinkedList::iterator::operator--(int b)
{
    LinkedList::iterator temp;
    temp.current_node = LinkedList::iterator::current_node;
    LinkedList::iterator::current_node = LinkedList::iterator::current_node -> prev;
    return temp;
}

LinkedList::iterator::~iterator() {}

///////////////////////////////////////////CONST_ITERATOR///////////////////////////////////////////////////////////////////
LinkedList::const_iterator::const_iterator() : LinkedList::base_iterator::base_iterator() {}

LinkedList::const_iterator::const_iterator(iterator & other) : LinkedList::base_iterator::base_iterator(other) {}

const value_type & LinkedList::const_iterator::operator*() const
{
    return (*LinkedList::const_iterator::current_node).value;
}

const value_type * LinkedList::const_iterator::operator->() const
{
    return &((*LinkedList::const_iterator::current_node).value);
}

LinkedList::const_iterator & LinkedList::const_iterator::operator++()
{
    LinkedList::base_iterator::move_straight();
    return *this;
}

LinkedList::const_iterator & LinkedList::const_iterator::operator--()
{
    LinkedList::base_iterator::move_back();
    return *this;
}

LinkedList::const_iterator LinkedList::const_iterator::operator++(int a)
{
    LinkedList::const_iterator temp;
    temp.current_node = LinkedList::const_iterator::current_node;
    LinkedList::const_iterator::current_node = LinkedList::const_iterator::current_node -> next;
    return temp;
}

LinkedList::const_iterator LinkedList::const_iterator::operator--(int b)
{
    LinkedList::const_iterator temp;
    temp.current_node = LinkedList::const_iterator::current_node;
    LinkedList::const_iterator::current_node = LinkedList::const_iterator::current_node -> prev;
    return temp;
}

LinkedList::const_iterator::~const_iterator() {}

///////////////////////////////////////////LINKED_LIST//////////////////////////////////////////////////////////////////////

LinkedList::LinkedList()
{
    LinkedList::list_size = 0;
    LinkedList::last_node = (LinkedList::node*) malloc (sizeof(LinkedList::node));
    LinkedList::first = LinkedList::last_node;
    LinkedList::last = LinkedList::last_node;
}


LinkedList::LinkedList(const LinkedList & other)
{
    LinkedList::list_size = other.size();
    if (LinkedList::list_size == 0)
    {
        LinkedList::last_node = (LinkedList::node*) malloc (sizeof(LinkedList::node));
        LinkedList::first = LinkedList::last_node;
        LinkedList::last = LinkedList::last_node;
    }
    else
    {
        LinkedList::first = (LinkedList::node*) malloc (sizeof(LinkedList::node));
        LinkedList::first -> value = other.first -> value;
        LinkedList::node *temp_this, *temp_other;
        temp_this = LinkedList::first;
        temp_other = other.first;
        for (int i = 1; i < LinkedList::list_size; i++)
        {
            temp_this = temp_this -> next;
            temp_other = temp_other -> next;
            temp_this = (LinkedList::node*) malloc (sizeof(LinkedList::node));
            temp_this -> value = temp_other -> value;
        }
    }
}

LinkedList::~LinkedList()
{
    free(LinkedList::last_node);
}

void LinkedList::clear()
{

}

value_type & LinkedList::front()
{
    return LinkedList::first -> value;
}

const value_type & LinkedList::front() const
{
    return LinkedList::front(); //абсолютно одинаквые реализации?
}

value_type & LinkedList::back()
{
    return LinkedList::last -> value;
}

const value_type & LinkedList::back() const
{
    return LinkedList::back();
}

LinkedList::iterator LinkedList::begin()
{

}

const LinkedList::iterator LinkedList::begin() const
{

}

const LinkedList::iterator LinkedList::cbegin() const
{

}

LinkedList::iterator LinkedList::end()
{

}

const LinkedList::iterator LinkedList::cend() const
{

}

const LinkedList::iterator LinkedList::end() const
{

}

bool LinkedList::contains(const value_type & value) const
{
    for (size_t i = 0; i < LinkedList::size(); i++)
    {

    }
}

size_t LinkedList::count(const value_type & value) const
{

}

size_t LinkedList::size() const
{
    return list_size;
}

bool LinkedList::empty() const
{
    if(!LinkedList::size()) return true;
    else return false;
}




