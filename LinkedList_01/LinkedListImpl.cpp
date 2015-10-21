#include "LinkedListImpl.h"

#define TEST_MODE

///////////////////////////////////////BASE_iterator_impl////////////////////////////////////////////////////////////////////
LinkedListImpl::base_iterator_impl::base_iterator_impl(node *my_node)
{
    current_node = my_node;
}

LinkedListImpl::base_iterator_impl::base_iterator_impl()
{
    current_node = nullptr;
}

LinkedListImpl::base_iterator_impl::base_iterator_impl(const base_iterator_impl & other)
{
    current_node = other.current_node;
}

void LinkedListImpl::base_iterator_impl::move_straight()
{
    if (current_node -> is_this_last_node())
    {
        throw (std::range_error(range_error_message));
    }

    current_node = current_node -> next;
}

void LinkedListImpl::base_iterator_impl::move_back()
{
    if (current_node -> is_this_first())
    {
        throw (std::range_error(range_error_message));
    }

    current_node = current_node -> prev;
}

bool LinkedListImpl::base_iterator_impl::operator!=(const base_iterator_impl & other) const
{
    if (current_node == other.current_node)
    {
        return false;
    }
    else
    {
        return true;
    }
}

bool LinkedListImpl::base_iterator_impl::operator==(const base_iterator_impl & other) const
{
    return !(operator!=(other));
}

void LinkedListImpl::base_iterator_impl::equate(const base_iterator_impl & other)
{
    if (&other == this)
    {
        return;
    }

    current_node = other.current_node;
}

LinkedListImpl::base_iterator_impl::~base_iterator_impl() {}


///////////////////////////////////////////iterator_impl/////////////////////////////////////////////////////////////////////////

LinkedListImpl::iterator_impl::iterator_impl() : base_iterator_impl() {}

LinkedListImpl::iterator_impl::iterator_impl(const iterator_impl & other) : base_iterator_impl(other) {}

LinkedListImpl::iterator_impl::iterator_impl(node *my_node) : base_iterator_impl(my_node) {}

LinkedListImpl::iterator_impl & LinkedListImpl::iterator_impl::operator=(const iterator_impl & other)
{
    equate(other);

    return *this;
}

value_type & LinkedListImpl::iterator_impl::operator*() const
{
	if (nullptr == current_node)
	{
		throw (std::range_error(range_error_message));	
	}

	if (true == current_node -> is_this_last_node())
	{
		throw (std::range_error(range_error_message));
	}

    return (*current_node).value;
}

value_type * LinkedListImpl::iterator_impl::operator->() const
{
	if (nullptr == current_node)
	{
		throw (std::range_error(range_error_message));	
	}

	if (true == current_node -> is_this_last_node())
	{
		throw (std::range_error(range_error_message));
	}

    return &((*current_node).value);
}

LinkedListImpl::iterator_impl & LinkedListImpl::iterator_impl::operator++()
{
    move_straight();

    return *this;
}

LinkedListImpl::iterator_impl & LinkedListImpl::iterator_impl::operator--()
{
    move_back();

    return *this;
}

LinkedListImpl::iterator_impl LinkedListImpl::iterator_impl::operator++(int a)
{
    if (current_node -> is_this_last_node())
    {
        throw (std::range_error(range_error_message));
    }

    iterator_impl temp (current_node);
    current_node = current_node -> next;

    return temp;
}

LinkedListImpl::iterator_impl LinkedListImpl::iterator_impl::operator--(int b)
{
    if (current_node -> is_this_first())
    {
        throw (std::range_error(range_error_message));
    }

    iterator_impl temp (current_node);
    current_node = current_node -> prev;

    return temp;
}

LinkedListImpl::iterator_impl::~iterator_impl() {}

///////////////////////////////////////////CONST_iterator_impl///////////////////////////////////////////////////////////////////
LinkedListImpl::const_iterator_impl::const_iterator_impl() : base_iterator_impl() {}

LinkedListImpl::const_iterator_impl::const_iterator_impl(const const_iterator_impl & other) : base_iterator_impl(other) {}

LinkedListImpl::const_iterator_impl::const_iterator_impl(node *my_node) : base_iterator_impl(my_node) {}

LinkedListImpl::const_iterator_impl::const_iterator_impl(const iterator_impl & other) : base_iterator_impl(other) {}

LinkedListImpl::const_iterator_impl & LinkedListImpl::const_iterator_impl::operator=(const const_iterator_impl & other)
{
    equate(other);

    return *this;
}

LinkedListImpl::const_iterator_impl & LinkedListImpl::const_iterator_impl::operator=(const iterator_impl & other)
{
    equate(other);

    return *this;
}

const value_type & LinkedListImpl::const_iterator_impl::operator*() const
{
	if (nullptr == current_node)
	{
		throw (std::range_error(range_error_message));

	}

	if (true == current_node -> is_this_last_node())
	{
		throw (std::range_error(range_error_message));
	}

    return (*current_node).value;
}

const value_type * LinkedListImpl::const_iterator_impl::operator->() const
{
	if (nullptr == current_node)
	{
		throw (std::range_error(range_error_message));
	}

	if (true == current_node -> is_this_last_node())
	{
		throw (std::range_error(range_error_message));
	}

    return &((*current_node).value);
}

LinkedListImpl::const_iterator_impl & LinkedListImpl::const_iterator_impl::operator++()
{
    move_straight();

    return *this;
}

LinkedListImpl::const_iterator_impl & LinkedListImpl::const_iterator_impl::operator--()
{
    move_back();

    return *this;
}

LinkedListImpl::const_iterator_impl LinkedListImpl::const_iterator_impl::operator++(int a)
{
    if (current_node -> is_this_last_node())
    {
        throw (std::range_error(range_error_message));;
    }

    const_iterator_impl temp(current_node);
    current_node = current_node -> next;

    return temp;
}

LinkedListImpl::const_iterator_impl LinkedListImpl::const_iterator_impl::operator--(int b)
{
    if (current_node -> is_this_first())
    {
        throw (std::range_error(range_error_message));
    }

    const_iterator_impl temp (current_node);
    current_node = current_node -> prev;

    return temp;
}

LinkedListImpl::const_iterator_impl::~const_iterator_impl() {}

///////////////////////////////////////////LINKED_LIST//////////////////////////////////////////////////////////////////////

LinkedListImpl::node* LinkedListImpl::create_new_node()
{
    node *temp;
    temp = new node;

    temp -> next = nullptr;
    temp -> prev = nullptr;
    temp -> value = value_type();

    return temp;
}

LinkedListImpl::LinkedListImpl()
{
    list_size = 0;
    last_node = create_new_node();
    first = last_node;
    last = last_node;
}

void LinkedListImpl::copy_list(const LinkedListImpl & other)
{
	if (0 != other.size())
	{
        for (const auto & v : other)
        {
            push_back(v);
        }
	}
}

void LinkedListImpl::destroy_list()
{
    clear();

    delete(last_node);
}

LinkedListImpl::LinkedListImpl(const LinkedListImpl & other) : LinkedListImpl()
{
    try
    {
        copy_list(other);

#ifdef TEST_MODE

        if (other.size() > 40)
        {
            throw (std::bad_alloc());
        }
        
#endif

    }
    catch (std::bad_alloc &error)
    {
        throw;
    }
}

LinkedListImpl::~LinkedListImpl()
{
    destroy_list();
}

void LinkedListImpl::clear()
{
	if (0 != size()) 
	{
    	erase(begin(), end());
    }
}

value_type & LinkedListImpl::front()
{
	if (first == last_node)
	{
        throw (std::range_error(empty_list_str));
	}

    return first -> value;
}

value_type & LinkedListImpl::back()
{
	if (last == last_node)
	{
        throw (std::range_error(empty_list_str));
	}

    return last -> value;
}

LinkedListImpl::iterator_impl LinkedListImpl::begin()
{
	if (first == last_node)
	{
        throw (std::range_error(empty_list_str));
	}

    iterator_impl my_iterator_impl(first);

    return my_iterator_impl;
}

const LinkedListImpl::iterator_impl LinkedListImpl::begin() const
{
    return cbegin();
}


const LinkedListImpl::iterator_impl LinkedListImpl::cbegin() const
{
	if (first == last_node)
	{
        throw (std::range_error(empty_list_str));
	}

    const iterator_impl my_iterator_impl(first);

    return my_iterator_impl;
}

LinkedListImpl::iterator_impl LinkedListImpl::end()
{
	if (last == last_node)
	{
        throw (std::range_error(empty_list_str));
	}

    iterator_impl my_iterator_impl(last_node);

    return my_iterator_impl;
}

const LinkedListImpl::iterator_impl LinkedListImpl::end() const
{
    return cend();
}

const LinkedListImpl::iterator_impl LinkedListImpl::cend() const
{
	if (last == last_node)
	{
        throw (std::range_error(empty_list_str));
	}

    const iterator_impl my_iterator_impl(last_node);

    return my_iterator_impl;
}

bool LinkedListImpl::contains(const value_type & value) const
{
	if (true == empty())
	{
		return false;
	}

    for (const auto & v : *this)
    {
        if (v == value) 
        {
            return true;
        }
    }

    return false;
}

size_t LinkedListImpl::count(const value_type & value) const
{
	if (true == empty())
	{
		return false;
	}

    size_t freq = 0;

    for (const auto & v : *this)
    {
        if (v == value) 
        {
            ++freq;
        }
    }

    return freq;
}

size_t LinkedListImpl::size() const
{
    return list_size;
}

bool LinkedListImpl::empty() const
{
    if (0 == size()) 
    {
    	return true;
    }
    else
    {
     	return false;
    }
}

LinkedListImpl::iterator_impl LinkedListImpl::erase (const_iterator_impl pos)
{
    node *cur_node = pos.current_node;

    if ((cur_node == last_node) || (pos.current_node == nullptr))
    {
        throw (std::range_error(empty_list_str));
    }

    node *next_node = pos.current_node -> next;
    node *prev_node = pos.current_node -> prev;
    next_node -> prev = prev_node;

    if (nullptr != prev_node) 
    {
        prev_node -> next = next_node;
    }
    else
    {
        first = next_node;
        first -> prev = nullptr;
    }

    if (last == cur_node)
    {
        if (1 == size())
        {
        	last = last_node;
        }
        else
        {
            last = prev_node;
        }
    }

    delete(cur_node);

    --list_size;

    return iterator_impl(next_node);
}

LinkedListImpl::iterator_impl LinkedListImpl::erase (const_iterator_impl begin, const_iterator_impl end)
{
    while (begin != end)
    {
        begin = erase(begin);
    }

    return iterator_impl(begin.current_node);
}


size_t LinkedListImpl::remove_all (const value_type & value)
{
    size_t freq = 0;

    while (remove_one(value))
    {
        ++freq;
    }

    return freq;
}

bool LinkedListImpl::remove_one (const value_type & value)
{
    if (0 == size())
    {
    	return false;
    }

    iterator_impl iter = begin();

    for (size_t i = 0; i < size(); i++)
    {
        if (*iter == value)
        {
            erase (iter);

            return true;
        }

        ++iter;
    }

    return false;
}

void LinkedListImpl::pop_back()
{
    if (0 == size())
    {
        throw (std::range_error(empty_list_str));
    }
    else
    {
        erase (--end());
    }
}

void LinkedListImpl::pop_front()
{
    if (0 == size())
    {
        throw (std::range_error(empty_list_str));
    }
    else 
    {
        erase (begin());
    }
}

void LinkedListImpl::push_back(const value_type & value)
{
    insert(iterator_impl(last_node), value);
}

void LinkedListImpl::push_front(const value_type & value)
{
    insert(iterator_impl(first), value);
}

LinkedListImpl::iterator_impl LinkedListImpl::insert(iterator_impl before, const value_type & value)
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

bool LinkedListImpl::operator!=(const LinkedListImpl & other) const
{
    return !(other == *this);
}
bool LinkedListImpl::operator==(const LinkedListImpl & other) const
{
    if (other.size() != size())
    {
    	return false;
    }

    if ((0 == size()) && (0 == other.size()))
    {
    	return true;
    }

    iterator_impl other_iter = other.cbegin();
    iterator_impl this_iter = cbegin();

    if (*other_iter != *this_iter) 
    {
        return false;
    }

    for (size_t i = 1; i < size(); i++)
    {
		++other_iter;
		++this_iter;

        if (*other_iter != *this_iter) 
        {
            return false;
        }
    }

    return true;
}

LinkedListImpl LinkedListImpl::operator+(const LinkedListImpl & other) const
{
	LinkedListImpl list2;

    list2+=*this;
    list2+=other;

    return list2;
}

LinkedListImpl & LinkedListImpl::operator+=(const LinkedListImpl & other)
{
	if (true == other.empty())
	{
		return *this;
	}

    for (const auto & v : other)
    {

#ifdef TEST_MODE

        if (size() >= 20)
        {
            throw (std::bad_alloc());
        }

#endif

        push_back(v);
    }

    return *this;
}

LinkedListImpl & LinkedListImpl::operator+=(const value_type & value)
{
    push_back(value);

    return *this;
}