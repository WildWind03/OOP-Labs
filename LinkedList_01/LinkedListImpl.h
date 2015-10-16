#include <cstdlib>
#include <string>
#include <iostream>
#include <stdexcept>

using namespace std;
using value_type = int;

class LinkedListImpl
{
private:
	struct node
		{
			value_type value;
			node *next;
			node *prev;

		    bool is_this_last_node()
		    {
		    	if (next == nullptr)
		    	{
		    		return true;
		    	}
		    	else
		    	{
		    		return false;
		    	}
		    }

		    bool is_next_last()
		    {
		    	if (is_this_last_node() == true)
		    	{
		    		throw (std::range_error("Next node is out of LinkedListImpl"));
		    	}
		     	if (next -> next == nullptr)
		        {
		        	return true;
		        }
		        else
		        {
		        	return false;
		        }
		    }
		    bool is_prev_last()
		    {
		      	if (prev == nullptr) 
		      	{
		      		return true;
		      	}
		      	else 
		      	{
		      		return false;
		      	}
		    }
		};

  	node *last_node;
  	node *last;
	node *first;
	size_t list_size;

	node *create_new_node ();
	void copy_list(const LinkedListImpl & other);

	class base_iterator_impl
	{
	private:
	    explicit base_iterator_impl(node *my_node);
	    base_iterator_impl();
	    base_iterator_impl(const base_iterator_impl & other);
	protected:
	    friend class LinkedListImpl;

	    node *current_node;
	    const string range_error_message = "Iterator is out of LinkedListImpl";

	    void move_straight();
	    void move_back();
	    void equate(const base_iterator_impl & other);
	public:
	    bool operator!=(const base_iterator_impl & other) const;
	    bool operator==(const base_iterator_impl & other) const;

	    ~base_iterator_impl();
	};

public:
	class iterator_impl : public base_iterator_impl
	{
    	explicit iterator_impl(node *my_node); 
  	public:
 	    friend class LinkedListImpl;

 	    iterator_impl();
		iterator_impl(const iterator_impl & other); 
		iterator_impl & operator=(const iterator_impl & other); 
	    value_type & operator*() const; 
	    value_type * operator->() const; 
	    iterator_impl & operator++(); 
	    iterator_impl operator++(int);
	    iterator_impl & operator--();
	    iterator_impl operator--(int);
	    ~iterator_impl();
	};

	class const_iterator_impl : public base_iterator_impl
	{
		explicit const_iterator_impl(node *my_node);
	public:
	    friend class LinkedListImpl;
		const_iterator_impl();
	    const_iterator_impl(const const_iterator_impl & other); ; 
	    const_iterator_impl(const iterator_impl & other); 
	    const_iterator_impl & operator=(const const_iterator_impl & other); 
	    const_iterator_impl & operator=(const iterator_impl & other); 
	    const value_type & operator*() const; 
	    const value_type * operator->() const; 
	    const_iterator_impl & operator++(); 
	    const_iterator_impl operator++(int); 
	    const_iterator_impl & operator--(); 
	    const_iterator_impl operator--(int); 
	    ~const_iterator_impl();
	};

	const string empty_list_str = "LinkedList is empty";

	LinkedListImpl(); 
	LinkedListImpl(const LinkedListImpl & other); 
	~LinkedListImpl();
	value_type & front(); 
	const value_type & front() const;
	value_type & back();
	const value_type & back() const;
	iterator_impl begin();
	const iterator_impl begin() const;
	const iterator_impl cbegin() const;
	iterator_impl end(); 
	const iterator_impl end() const;
	const iterator_impl cend() const;
	bool contains(const value_type & value) const;
	size_t count(const value_type & value) const;
	size_t size() const;
	bool empty() const;
	iterator_impl erase(const_iterator_impl pos);
	iterator_impl erase(const_iterator_impl begin, const_iterator_impl end);
	void clear();
	size_t remove_all(const value_type & value);
	bool remove_one(const value_type & value);
	void pop_back();
	void pop_front();
	void push_back(const value_type & value); 
	void push_front(const value_type & value); 
	iterator_impl insert(iterator_impl before, const value_type & value); 
	bool operator!=(const LinkedListImpl & other) const; 
	bool operator==(const LinkedListImpl & other) const;
	LinkedListImpl operator+(const LinkedListImpl & other) const; 
	LinkedListImpl & operator+=(const LinkedListImpl & other); 
	LinkedListImpl & operator+=(const value_type & value); 
};
