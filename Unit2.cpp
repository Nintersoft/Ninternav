//---------------------------------------------------------------------------

#include <fmx.h>
#pragma hdrstop

#include "Unit2.h"
#include "Unit1.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.fmx"
TForm2 *Form2;
//---------------------------------------------------------------------------
// declaração de variáveis - var declarations
bool a = false , b = false, c = false, d = false;
//---------------------------------------------------------------------------
__fastcall TForm2::TForm2(TComponent* Owner)
	: TForm(Owner)
{
}
//---------------------------------------------------------------------------
void __fastcall TForm2::Edit1Typing(TObject *Sender)
{
	a = true;
}
//---------------------------------------------------------------------------
void __fastcall TForm2::Edit1KeyDown(TObject *Sender, WORD &Key, System::WideChar &KeyChar,
		  TShiftState Shift)
{
	if (Key == vkReturn) {
		WebBrowser1->Navigate(Edit1->Text);
		Edit1->Text=Edit1->Text;
		Key=0;
	}
}
//---------------------------------------------------------------------------
void __fastcall TForm2::Edit1Exit(TObject *Sender)
{
	a = false;
}
//---------------------------------------------------------------------------
void __fastcall TForm2::SpeedButton1Click(TObject *Sender)
{
	if (a == true && SpeedButton1->Position->Y < 100) {
		Timer2->Enabled = true;
		WebBrowser1->Navigate(Edit1->Text);
	}
	else {
		Timer1->Enabled = true;
	}
}
//---------------------------------------------------------------------------

void __fastcall TForm2::Timer1Timer(TObject *Sender)
{
	if (b == false) {
		if ( SpeedButton1->Position->Y < 100) {
			SpeedButton1->Position->Y+=10;
			WebBrowser1->Position->Y+=10;
			if (SpeedButton1->Position->Y >= 48) {
				Button1->Visible = true;
				Button1->Position->Y+=10;
			}
		}
		else {
			b = true;
			Timer1->Enabled = false;
		}
	}
	else {
		if ( SpeedButton1->Position->Y > 1) {
			if (SpeedButton1->Position->Y < 48) {
				Button1->Visible = false;
			}
			else {
				Button1->Position->Y-=10;
			}
			SpeedButton1->Position->Y-=10;
			WebBrowser1->Position->Y-=10;
		}
		else {
			b = false;
			Timer1->Enabled = false;
		}
	}
}
//---------------------------------------------------------------------------


void __fastcall TForm2::Button1Click(TObject *Sender)
{
	Timer1->Enabled = true;
	Form1->Show();
}
//---------------------------------------------------------------------------

void __fastcall TForm2::Timer2Timer(TObject *Sender)
{
	if (d == false) {
		if (WebBrowser1->Position->Y > 0) {
			Header1->Position->Y-=10;
			SpeedButton1->Position->Y-=10;
			Edit1->Position->Y-=10;
			WebBrowser1->Position->Y-=10;
			WebBrowser1->Height+=10;
		}
		else{
			d = true;
			Timer2->Enabled = false;
		}
	}
	if (d == true) {
		if (WebBrowser1->Position->Y < 48) {
			Header1->Position->Y+=10;
			SpeedButton1->Position->Y+=10;
			Edit1->Position->Y+=10;
			WebBrowser1->Position->Y+10;
			WebBrowser1->Height-=10;
		}
		else {
			d = false;
			Timer2->Enabled = false;
		}
	}
}
//---------------------------------------------------------------------------
void __fastcall TForm2::WebBrowser1DidFinishLoad(TObject *ASender)
{
	Edit1->Text = WebBrowser1->URL;
}
//---------------------------------------------------------------------------

void __fastcall TForm2::WebBrowser1DidFailLoadWithError(TObject *ASender)
{
	Edit1->Text = "ninternav:erro";
}
//---------------------------------------------------------------------------

void __fastcall TForm2::Action1Execute(TObject *Sender)
{
	Form1->Show();
}
//---------------------------------------------------------------------------

void __fastcall TForm2::Action2Execute(TObject *Sender)
{
	if (d == false )  {
		Timer2->Enabled = true;
	}
}
//---------------------------------------------------------------------------

void __fastcall TForm2::Action3Execute(TObject *Sender)
{
	if (d == true) {
		Timer2->Enabled = true;
	}
}
//---------------------------------------------------------------------------
