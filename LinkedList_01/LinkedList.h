#include <cstdlib>

using value_type = int;

class LinkedList
{
private:
  struct node
	{
		value_type value;
		node *next;
		node *prev;
    bool is_next_last()
    {
      if (next -> next == nullptr) return true;
      else return false;
    }
    bool is_prev_last()
    {
      if (prev == nullptr) return true;
      else return false;
    }
	};
  node *last_node;
  node *last;
	node *first;
	size_t list_size;
	node *create_new_node ();
	void copy_list(const LinkedList & other);
  class base_iterator
  {
  private:
    explicit base_iterator(node *my_node);
    base_iterator();
    base_iterator(const base_iterator & other);
  protected:
    friend class LinkedList;
    node *current_node = nullptr;
    void move_straight();
    void move_back();
    void equate(const base_iterator & other);
  public:
    bool operator!=(const base_iterator & other) const;
    bool operator==(const base_iterator & other) const;
    ~base_iterator();
  };
public:
  class const_iterator;
  class iterator;
	class iterator : public base_iterator
	{
  public:
		iterator();
		iterator(const iterator & other); 
    explicit iterator(node *my_node); 
		iterator & operator=(const iterator & other); 
    value_type & operator*() const; 
    value_type * operator->() const; 
    iterator & operator++(); 
    iterator operator++(int);
    iterator & operator--();
    iterator operator--(int);
    ~iterator();
	};
	class const_iterator : public base_iterator
	{
  public:
    const_iterator(); //
    const_iterator(const const_iterator & other); 
    explicit const_iterator(node *my_node); 
    const_iterator(const iterator & other); 
    const_iterator & operator=(const const_iterator & other); 
    const_iterator & operator=(const iterator & other); 
    const value_type & operator*() const; 
    const value_type * operator->() const; 
    const_iterator & operator++(); 
    const_iterator operator++(int); 
    const_iterator & operator--(); 
    const_iterator operator--(int); 
    ~const_iterator();
	};
	LinkedList(); 
  LinkedList(const LinkedList & other); 
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
