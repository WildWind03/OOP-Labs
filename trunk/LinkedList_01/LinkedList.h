#include <cstring>

using value_type = int;
class LinkedList
{
    struct node
	{
		value_type value = value_type();
		node *next = nullptr;
		node *prev = nullptr;
	};
    node last_node;
    node *last;
	node *first;
	size_t list_size;
public:
	class iterator
	{
        node *current_node = nullptr;
	public:
		iterator();
		iterator(const iterator & other);
		iterator & operator=(const iterator & other);
    	bool operator!=(const iterator & other) const;
    	bool operator==(const iterator & other) const;
    	value_type & operator*() const;
    	value_type * operator->() const;
    	iterator & operator++();
    	iterator operator++(int);
    	iterator & operator--();
    	iterator operator--(int);
    	~iterator();
	};
	LinkedList();
  	LinkedList(const LinkedList & other);
  	~LinkedList();
  	value_type & front();
  	const value_type & front() const;
  	value_type & back();
  	const value_type & back() const;
  	iterator begin();
  	iterator end();
  	bool contains(const value_type & value) const;
  	size_t count(const value_type & value) const;
  	size_t size() const;
  	bool empty() const;

  	iterator erase(iterator pos);
  	iterator erase(iterator begin, iterator end);
  	void clear();
  	size_t remove_all(const value_type & value);
  	bool remove_one(const value_type & value);
  	void pop_back();
  	void pop_front();
  	void push_back(const value_type & value);
  	void push_front(const value_type & value);
  	iterator insert(iterator before, const value_type & value);
  	bool operator!=(const LinkedList & other) const;
  	bool operator==(const LinkedList & other) const;
  	LinkedList operator+(const LinkedList & other) const;
  	LinkedList & operator+=(const LinkedList & other);
  	LinkedList & operator+=(const value_type & value);
  	LinkedList & operator=(const LinkedList & other);
};
