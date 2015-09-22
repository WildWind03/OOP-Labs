#include "LinkedList.h"

#include <iostream>

int main()
{
    return 0;
}

LinkedList::iterator::iterator()
{
    LinkedList::iterator::current_node = nullptr;
}

LinkedList::iterator::iterator(const iterator & other)
{
    LinkedList::iterator::current_node = other.current_node;
}

LinkedList::iterator& LinkedList::iterator::operator=(const iterator & other)
{
    LinkedList::iterator::current_node = other.current_node;
    return *this;
}

bool LinkedList::iterator::operator!=(const iterator & other) const
{
    if (LinkedList::iterator::current_node == other.current_node) return true;
    else return false;
}

bool LinkedList::iterator::operator==(const iterator & other) const
{
    return !LinkedList::iterator::operator!=(other);
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
    LinkedList::iterator::current_node = LinkedList::iterator::current_node -> next;
    return *this;
}

LinkedList::iterator & LinkedList::iterator::operator--()
{
    LinkedList::iterator::current_node = LinkedList::iterator::current_node -> next;
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

LinkedList::LinkedList()
{
    LinkedList::list_size = 0;
    LinkedList::first = &LinkedList::last_node;
    LinkedList::last = &LinkedList::last_node;
}


LinkedList::LinkedList(const LinkedList & other)
{
    LinkedList::list_size = other.size();
    LinkedList::first = &other.first;
    LinkedList::last = &other.last;
    //динамический послед элемент
}

LinkedList::~LinkedList()
{

}
