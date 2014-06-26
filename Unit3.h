//---------------------------------------------------------------------------

#ifndef Unit3H
#define Unit3H
//---------------------------------------------------------------------------
#include <System.Classes.hpp>
#include <FMX.Controls.hpp>
#include <FMX.Forms.hpp>
#include <FMX.Header.hpp>
#include <FMX.StdCtrls.hpp>
#include <FMX.Types.hpp>
#include <FMX.Layouts.hpp>
#include <FMX.Memo.hpp>
#include <FMX.Objects.hpp>
#include <FMX.Edit.hpp>
//---------------------------------------------------------------------------
class TForm3 : public TForm
{
__published:	// IDE-managed Components
	THeader *Header1;
	TLabel *Ninternav;
	TSpeedButton *SpeedButton1;
	THeader *Footer1;
	TLabel *Label1;
	TCheckBox *CheckBox1;
	TButton *Button1;
	TLabel *Label2;
	void __fastcall SpeedButton1Click(TObject *Sender);
	void __fastcall Button1Click(TObject *Sender);
	void __fastcall Label2Click(TObject *Sender);
private:	// User declarations
public:		// User declarations
	__fastcall TForm3(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TForm3 *Form3;
//---------------------------------------------------------------------------
#endif
