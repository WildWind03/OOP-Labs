#include "FieldView.h"

FieldView::FieldView(const Field & myField, const ShotField & shots) : myField(myField), shots(shots) 
{
	
}

size_t FieldView::getWidth() const
{
	return myField.getWidth();
}
size_t FieldView::getHeight() const
{
	return myField.getHeight();
}
size_t FieldView::getSize() const
{
	return myField.getSize();
}

FieldView::~FieldView()
{
	
}