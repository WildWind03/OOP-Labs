#include <cstdlib>
#include <string>
#include <iostream>
#include <stdexcept>
#include "LinkedListImpl.h"

using namespace std;
using value_type = int;

class LinkedList
{
private:
	LinkedListImpl *list;

public:
	class const_iterator;

	class iterator
	{
		LinkedListImpl::iterator_impl *iter_impl;
  	public:
 		friend class LinkedList;

 	    iterator();
		iterator(const iterator & other); 
		iterator & operator=(const iterator & other); 
	    value_type & operator*() const; 
	    value_type * operator->() const; 
	    iterator & operator++(); 
	    iterator operator++(int);
	    iterator & operator--();
	    iterator operator--(int);
		bool operator!=(const const_iterator & other) const;
		bool operator==(const const_iterator & other) const;
	    ~iterator();
	};

	class const_iterator
	{
		LinkedListImpl::const_iterator_impl *const_iter_impl;

	public:
		friend class LinkedList;

		const_iterator();
	    const_iterator(const const_iterator & other); ; 
	    const_iterator(const iterator & other); 
	    const_iterator & operator=(const const_iterator & other); 
	    const_iterator & operator=(const iterator & other); 
	    const value_type & operator*() const; 
	    const value_type * operator->() const; 
	    const_iterator & operator++(); 
	    const_iterator operator++(int); 
	    const_iterator & operator--(); 
	    const_iterator operator--(int); 
		bool operator!=(const const_iterator & other) const;
		bool operator==(const const_iterator & other) const;
	    ~const_iterator();
	};

	LinkedList(); 
	LinkedList(const LinkedList & other); 
	LinkedList(LinkedList && other);
	~LinkedList();
	value_type & front(); 
	const value_type & front() const;
	value_type & back();
	const value_type & back() const;
	iterator begin();
	const iterator begin() const;
	const iterator cbegin() const;
	iterator end(); 
	const iterator end() const;
	const iterator cend() const;
	bool contains(const value_type & value) const;
	size_t count(const value_type & value) const;
	size_t size() const;
	bool empty() const;
	iterator erase(const_iterator pos);
	iterator erase(const_iterator begin, const_iterator end);
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
	LinkedList & operator=(LinkedList && other);
};
