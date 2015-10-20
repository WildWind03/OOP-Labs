#include "LinkedList.h"

///////////////////////////////////////////ITERATOR/////////////////////////////////////////////////////////////////////////

LinkedList::iterator::iterator()
{
	iter_impl = new LinkedListImpl::iterator_impl;
}

LinkedList::iterator::iterator(const iterator & other)
{
	iter_impl = new LinkedListImpl::iterator_impl (*(other.iter_impl));
}

bool LinkedList::iterator::operator!=(const const_iterator & other) const
{
	return !operator==(other);
}

bool LinkedList::iterator::operator==(const const_iterator & other) const
{
	return iter_impl -> operator==(*(other.const_iter_impl));
}

LinkedList::iterator & LinkedList::iterator::operator=(const iterator & other)
{
	iter_impl -> operator=(*other.iter_impl);
    return *this;
}

value_type & LinkedList::iterator::operator*() const
{
	return iter_impl -> operator*();
}

value_type * LinkedList::iterator::operator->() const
{
	return iter_impl -> operator->();
}

LinkedList::iterator & LinkedList::iterator::operator++()
{
    iter_impl -> operator++();
    return *this;
}

LinkedList::iterator & LinkedList::iterator::operator--()
{
    iter_impl -> operator--();
    return *this;
}

LinkedList::iterator LinkedList::iterator::operator++(int a)
{
	iter_impl -> operator++(a);
	return *this;
}

LinkedList::iterator LinkedList::iterator::operator--(int b)
{
	iter_impl -> operator--(b);
	return *this;
}

LinkedList::iterator::~iterator() 
{
	delete (iter_impl);
}

///////////////////////////////////////////CONST_ITERATOR///////////////////////////////////////////////////////////////////
LinkedList::const_iterator::const_iterator()
{
	const_iter_impl = new LinkedListImpl::const_iterator_impl;
}

LinkedList::const_iterator::const_iterator(const const_iterator & other)
{
	const_iter_impl = new LinkedListImpl::const_iterator_impl (*(other.const_iter_impl));
}

LinkedList::const_iterator::const_iterator(const iterator & other)
{
	const_iter_impl = new LinkedListImpl::const_iterator_impl (*(other.iter_impl));
}

bool LinkedList::const_iterator::operator!=(const const_iterator & other) const
{
	return !operator==(other);
}

bool LinkedList::const_iterator::operator==(const const_iterator & other) const
{
	return const_iter_impl -> operator==(*(other.const_iter_impl));
}

LinkedList::const_iterator & LinkedList::const_iterator::operator=(const const_iterator & other)
{
    const_iter_impl -> operator=(*(other.const_iter_impl));
    return *this;
}

LinkedList::const_iterator & LinkedList::const_iterator::operator=(const iterator & other)
{
    const_iter_impl -> operator=(*(other.iter_impl));
    return *this;
}

const value_type & LinkedList::const_iterator::operator*() const
{
	return const_iter_impl -> operator*();
}

const value_type * LinkedList::const_iterator::operator->() const
{
	return const_iter_impl -> operator->();
}

LinkedList::const_iterator & LinkedList::const_iterator::operator++()
{
	const_iter_impl -> operator++();
	return *this;
}

LinkedList::const_iterator & LinkedList::const_iterator::operator--()
{
	const_iter_impl -> operator--();
	return *this;
}

LinkedList::const_iterator LinkedList::const_iterator::operator++(int a)
{
	const_iter_impl -> operator++(a);
	return *this;
}

LinkedList::const_iterator LinkedList::const_iterator::operator--(int b)
{
	const_iter_impl -> operator--(b);
	return *this;
}

LinkedList::const_iterator::~const_iterator() 
{
	delete (const_iter_impl);
}

///////////////////////////////////////LINKED_LIST//////////////////////////////////////////////////////////////////////

LinkedList::LinkedList(LinkedList && other)
{
    list = other.list;
    other.list = nullptr;
}

LinkedList::LinkedList()
{
	list = new LinkedListImpl;
}

LinkedList::LinkedList(const LinkedList & other)
{
	list = new LinkedListImpl(*(other.list));
}

LinkedList::~LinkedList()
{
    delete(list);
}

void LinkedList::clear()
{
	list -> clear();
}

value_type & LinkedList::front()
{
    return list -> front();
}

const value_type & LinkedList::front() const
{
    return list -> front();
}

value_type & LinkedList::back()
{
	return list -> back();
}

const value_type & LinkedList::back() const
{
	return list -> back();
}

LinkedList::iterator LinkedList::begin()
{
    iterator my_iterator;
    *(my_iterator.iter_impl) = list -> begin();
    return my_iterator;
}

const LinkedList::iterator LinkedList::begin() const
{
    return cbegin();
}

const LinkedList::iterator LinkedList::cbegin() const
{
    iterator my_iterator;
    *(my_iterator.iter_impl) = list -> cbegin();
    return my_iterator;
}

LinkedList::iterator LinkedList::end()
{
    iterator my_iterator;
    *(my_iterator.iter_impl) = list -> end();
    return my_iterator;
}

const LinkedList::iterator LinkedList::cend() const
{
    iterator my_iterator;
    *(my_iterator.iter_impl) = list -> cend();
    return my_iterator;
}

const LinkedList::iterator LinkedList::end() const
{
    return cend();
}

bool LinkedList::contains(const value_type & value) const
{
	return list -> contains(value);
}

size_t LinkedList::count(const value_type & value) const
{
	return list -> count(value);
}

size_t LinkedList::size() const
{
    return list -> size();
}

bool LinkedList::empty() const
{
	return list -> empty();
}

LinkedList::iterator LinkedList::erase (const_iterator pos)
{
	iterator my_iter;
	(*my_iter.iter_impl) = list -> erase (*(pos.const_iter_impl));
	return my_iter;
}

LinkedList::iterator LinkedList::erase (const_iterator begin, const_iterator end)
{
	iterator my_iter;
	(*my_iter.iter_impl) = list -> erase (*(begin.const_iter_impl), *(end.const_iter_impl));
	return my_iter;
}


size_t LinkedList::remove_all (const value_type & value)
{
	return list -> remove_all(value);
}

bool LinkedList::remove_one (const value_type & value)
{
	return list -> remove_one(value);
}

void LinkedList::pop_back()
{
	list -> pop_back();
}

void LinkedList::pop_front()
{
	list -> pop_front();
}

void LinkedList::push_back(const value_type & value)
{
    list -> push_back(value);
}

void LinkedList::push_front(const value_type & value)
{
	list -> push_front(value);
}

LinkedList::iterator LinkedList::insert(iterator before, const value_type & value)
{
	iterator my_iter;
	*(my_iter.iter_impl) = list -> insert (*(before.iter_impl), value);
	return my_iter;
}

bool LinkedList::operator!=(const LinkedList & other) const
{
    return list -> operator!=(*(other.list));
}
bool LinkedList::operator==(const LinkedList & other) const
{
	return list -> operator==(*(other.list));
}

LinkedList LinkedList::operator+(const LinkedList & other) const
{
	LinkedListImpl *impl_list = new LinkedListImpl (list -> operator+(*(other.list)));
	LinkedList new_list;
	delete (new_list.list);
	new_list.list = impl_list;
	return new_list;
}

LinkedList & LinkedList::operator+=(const LinkedList & other)
{
	list -> operator+=(*(other.list));
	return *this;
}
LinkedList & LinkedList::operator+=(const value_type & value)
{
    list -> operator+=(value);
    return *this;
}
LinkedList & LinkedList::operator=(const LinkedList & other)
{
	if (*this == other)
	{
		return *this;
	}
	LinkedList copy_of_list(other);
	delete(list);
	list = copy_of_list.list;
	copy_of_list.list = nullptr;
	return *this;
}

LinkedList & LinkedList::operator=(LinkedList && other)
{
    delete(list);
    list = other.list;
    other.list = nullptr;
    return *this;
}
