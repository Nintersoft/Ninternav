//---------------------------------------------------------------------------

#include <fmx.h>
#pragma hdrstop

#include "Unit1.h"
#include "Unit2.h"
#include "Unit3.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.fmx"
TForm1 *Form1;
//---------------------------------------------------------------------------
//declaração de variáveis - var declarations;
bool a=false;
float sbh, sbht, sbhd; //encontro de altura do speedbutton
//---------------------------------------------------------------------------
__fastcall TForm1::TForm1(TComponent* Owner)
	: TForm(Owner)
{
}
//---------------------------------------------------------------------------
void __fastcall TForm1::SpeedButton2Click(TObject *Sender)
{
	Form2->WebBrowser1->Navigate(Edit1->Text);
	Form2->Edit1->Text=Edit1->Text;
	Form2->Show();
}
//---------------------------------------------------------------------------
void __fastcall TForm1::Edit1KeyDown(TObject *Sender, WORD &Key, System::WideChar &KeyChar,
		  TShiftState Shift)
{
	if (Key == vkReturn) {
		Form2->WebBrowser1->Navigate(Edit1->Text);
		Form2->Edit1->Text=Edit1->Text;
		Form2->Show();
		Key=0;
	}
}
//---------------------------------------------------------------------------
void __fastcall TForm1::Timer1Timer(TObject *Sender)
{
	if (a == false) {
		if (sbht > sbhd) {
			Label1->Visible = false;
			SpeedButton1->Position->Y-=5;
			Button1->Position->Y-=5;
			sbht = SpeedButton1->Position->Y;
		}
		else {
			a=true;
			Timer1->Enabled = false;
		}
	}
	else {
		if (sbht < sbh) {
			Label1->Visible = true;
			SpeedButton1->Position->Y+=5;
			Button1->Position->Y+=5;
			sbht = SpeedButton1->Position->Y;
		}
		else {
			a=false;
			Timer1->Enabled = false;
		}
	}
}
//---------------------------------------------------------------------------
void __fastcall TForm1::SpeedButton1Click(TObject *Sender)
{
	Timer1->Enabled = true;
}
//---------------------------------------------------------------------------
void __fastcall TForm1::FormCreate(TObject *Sender)
{
	sbh = SpeedButton1->Position->Y;
	sbht = SpeedButton1->Position->Y;
	sbhd = sbh-82;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::FormResize(TObject *Sender)
{
	sbh = SpeedButton1->Position->Y;
	sbht = SpeedButton1->Position->Y;
	sbhd = sbh-82;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button1Click(TObject *Sender)
{
    Timer1->Enabled = true;
	Form3->Show();
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Action1Execute(TObject *Sender)
{
	Form2->Show();
}
//---------------------------------------------------------------------------

